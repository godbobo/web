package com.aqzscn.www.global.component;

import com.aqzscn.www.global.domain.co.AppException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件上传下载服务
 *
 * @author Godbobo
 * @date 2019/6/1
 */
@Component
public class FileService {

    private final HttpServletResponse response;
    private final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Value("${myfile.export}")
    private String exportPath;

    public FileService(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * 批量下载纯文本文件
     *
     * @param contentList  文件内容列表
     * @param fileNameList 文件名列表 两者必须一一对应
     * @param fileType     文件类型 需要自己加.
     * @throws RuntimeException 运行时异常
     */
    public void downloadPlains(List<String> contentList, List<String> fileNameList, String fileType) throws RuntimeException {
        if (contentList == null || fileNameList == null || StringUtils.isBlank(fileType) || contentList.size() != fileNameList.size() || fileNameList.size() == 0) {
            throw AppException.of("文件信息不完整，无法下载！");
        }
        try {
            // 创建一个唯一的目录
            Random random = new Random();
            String directoryPath = exportPath + "\\" + System.currentTimeMillis() + random.nextInt(1000);
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            // 将列表中的内容生成文件
            List<File> fileList = new ArrayList<>();
            Map<String, Integer> dup = new HashMap<>(contentList.size());
            for (int i = 0; i < contentList.size(); i++) {
                String fileName = fileNameList.get(i);
                // 将文件名保存到map中，对重名文件进行判断
                if (dup.containsKey(fileName)) {
                    dup.replace(fileName, dup.get(fileName) + 1);
                    fileName = fileName + "(" + dup.get(fileName) + ")";
                } else {
                    dup.put(fileName, 0);
                }
                File file = new File(directoryPath + "\\" + fileName + fileType);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(contentList.get(i).getBytes(StandardCharsets.UTF_8));
                bos.flush();
                bos.close();
                fos.close();
                fileList.add(file);
            }
            // 压缩并写入到响应
            downloadZip(fileList);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw AppException.of("生成文件时发生异常");
        }
    }

    /**
     * 下载压缩文件
     *
     * @param files 文件列表
     * @throws RuntimeException 运行时异常
     */
    public void downloadZip(List<File> files) throws RuntimeException {
        if (files == null || files.size() == 0) {
            throw AppException.of("文件信息不完整，无法下载！");
        }
        try {
            // 创建一个临时的压缩文件，后缀可以是rar或zip
            Random random = new Random();
            String fileName = System.currentTimeMillis() + "" + random.nextInt(1000) + ".zip";
            File file = new File(exportPath + "\\" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            // 创建文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            // 打包文件时使用ZipOutputStream 因此需要转换输出流
            ZipOutputStream zos = new ZipOutputStream(fos);
            // 将列表中的文件一个个打包
            for (File f : files) {
                zipFile(f, zos);
            }
            zos.close();
            fos.close();
            // 将文件写入到响应
            download(file);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw AppException.of("生成文件时发生异常");
        } finally {
            // 删除传入的文件
            for (File f : files) {
                deleteTempFile(f.getPath());
            }
        }
    }


    /**
     * 将文件写入到响应中 最后删除文件
     *
     * @param file 文件
     * @throws RuntimeException 运行时异常
     */
    public void download(File file) throws RuntimeException {
        if (file == null || !file.exists()) {
            throw AppException.of("生成文件失败，无法下载！");
        }
        try {
            // 以流的形式下载文件
            InputStream is = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] b = new byte[is.available()];
            is.read(b);
            is.close();

            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            os.write(b);
            os.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw AppException.of("写入文件时发生异常");
        } finally {
            deleteTempFile(file.getPath());
        }
    }

    /**
     * 删除缓存的文件
     *
     * @param filePath 文件路径
     */
    public void deleteTempFile(String filePath) {
        if (!StringUtils.isBlank(filePath)) {
            try {
                File file = new File(filePath);
                file.delete();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }


    /**
     * 打包单个文件
     * 跳过不存在的文件 对于目录则取出目录中的文件，忽略目录
     *
     * @param file 文件
     * @param zos  压缩输出流 需调用方自行关闭
     */
    public void zipFile(File file, ZipOutputStream zos) {
        try {
            if (file.exists()) {
                // 只针对文件 目录形式的暂时不做
                if (file.isFile()) {
                    FileInputStream in = new FileInputStream(file);
                    BufferedInputStream buffer = new BufferedInputStream(in, 512);
                    ZipEntry entry = new ZipEntry(file.getName());
                    zos.putNextEntry(entry);
                    // 向压缩文件中写入数据
                    int n;
                    byte[] bf = new byte[512];
                    while ((n = buffer.read(bf)) != -1) {
                        zos.write(bf, 0, n);
                    }
                    // 关闭创建的流对象
                    buffer.close();
                    in.close();
                } else {
                    File[] files = file.listFiles();
                    if (files != null) {
                        for (File file1 : files) {
                            zipFile(file1, zos);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
