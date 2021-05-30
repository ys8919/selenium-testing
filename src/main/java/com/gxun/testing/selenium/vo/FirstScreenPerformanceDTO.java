package com.gxun.testing.selenium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirstScreenPerformanceDTO {
    private Long PageDomNum;
    private Double domContentLoadedCost;
    private Double loadEventCost;
    private Double pageSize;
    private Integer pageRequestNum;
    private PageErrorsDTO pageErrorsDTO;


}
