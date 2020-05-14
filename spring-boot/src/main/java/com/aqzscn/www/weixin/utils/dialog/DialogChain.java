package com.aqzscn.www.weixin.utils.dialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 对话过滤器链条
 * @author Godbobo
 * @version 1.0
 * @date 2020/5/13 12:40
 */
public class DialogChain implements DialogFilter {

    private List<DialogFilter> ds = new ArrayList<>();
    private int index = 0;

    DialogChain(){

    }

    public DialogChain addHandler(DialogFilter dialogFilter) {
        ds.add(dialogFilter);
        return this;
    }

    @Override
    public void doAnswer(Dialog dialog, DialogChain chain) {
        if (index == ds.size()) {
            return;
        }
        DialogFilter df = ds.get(index++);
        df.doAnswer(dialog, chain);
    }
}
