package com.aqzscn.www.weixin.domain.vo;

import lombok.Data;

/**
 * @author Godbobo
 * @version 1.0
 * @date 2019/9/6 18:46
 */
@Data
public class Hitokoto {

    private Long id;

    private String hitokoto;

    private String type;

//    private String typeName;

    private String from;

    private String creator;

    private String created_at;

}
