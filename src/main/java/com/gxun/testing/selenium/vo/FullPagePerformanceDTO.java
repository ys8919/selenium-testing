package com.gxun.testing.selenium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullPagePerformanceDTO {
    private Long pageDomNum;
    private Double domContentLoadedCost;
    private Double loadEventCost;
    private Double pageSize;
    private Integer pageRequestNum;
    private PageErrorsDTO pageErrorsDTO;
}
