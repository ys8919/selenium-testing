package com.gxun.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 杨
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Script {
    private String id;
    private String scriptName;
    private String processId;
    private String startTime;
    private String endTime;
    private Integer type;
}
