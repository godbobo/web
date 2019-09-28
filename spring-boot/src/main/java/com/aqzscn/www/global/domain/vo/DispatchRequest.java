package com.aqzscn.www.global.domain.vo;

import com.aqzscn.www.global.mapper.Dispatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DispatchRequest extends Dispatch implements Serializable {

    private Integer qEnable;
}
