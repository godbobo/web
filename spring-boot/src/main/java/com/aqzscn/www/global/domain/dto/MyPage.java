package com.aqzscn.www.global.domain.dto;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * 分页结果对象
 * Created by Godbobo on 2019/5/25.
 */
@Data
public class MyPage {

    private List lst;

    private Integer page; // 当前页码

    private Integer rows; // 当前显示数量

    private Long total; // 总数量

    public MyPage() {}

    public MyPage(PageInfo info) {
        this.page = info.getPageNum();
        this.rows = info.getPageSize();
        this.total = info.getTotal();
        this.lst = info.getList();
    }

}
