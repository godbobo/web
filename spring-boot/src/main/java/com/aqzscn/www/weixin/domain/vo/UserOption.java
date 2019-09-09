package com.aqzscn.www.weixin.domain.vo;

import lombok.Data;

/**
 * 记录用户当前操作
 * @author Godbobo
 * @version 1.0
 * @date 2019/9/7 9:23
 */
@Data
public class UserOption {

    // 功能编号
    private String menuId;

    // 功能名称
    private String menuName;

}
