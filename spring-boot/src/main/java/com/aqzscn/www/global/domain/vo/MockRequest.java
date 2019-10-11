package com.aqzscn.www.global.domain.vo;

import com.aqzscn.www.global.mapper.Mock;

import java.io.Serializable;

public class MockRequest extends Mock implements Serializable {

    private String dispatchName;

    public String getDispatchName() {
        return dispatchName;
    }

    public void setDispatchName(String dispatchName) {
        this.dispatchName = dispatchName;
    }
}
