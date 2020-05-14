package com.aqzscn.www.weixin.utils.dialog;

/**
 * 对话处理链条
 * @author Godbobo
 */
public interface DialogFilter {

    /**
     * 作出回复
     * @param dialog 对话信息
     * @param chain 过滤器列表
     */
    void doAnswer(Dialog dialog, DialogChain chain);

}
