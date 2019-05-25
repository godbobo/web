package com.aqzscn.www.global.domain.dto;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * Created by Godbobo on 2019/5/25.
 */
@Data
public class MyPage {

    private List lst;

    private int page; // 当前页码

    private int rows; // 当前显示数量

    private long total; // 总数量

    public MyPage(PageInfo info) {
        this.page = info.getPageNum();
        this.rows = info.getPageSize();
        this.total = info.getTotal();
        this.lst = info.getList();
    }

}
