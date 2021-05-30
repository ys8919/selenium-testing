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
public class WebScript {
    private String id;
    private String scriptName;
    private String browserName;
    private String systemName;
    private String startTime;
    private String endTime;
    private String loadTime;
    private String network;
    private Integer requestNumber;
    private Integer type;
}
