package com.gxun.testing.selenium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalPerformanceDTO {
    private FirstScreenPerformanceDTO firstScreenPerformance;
    private FullPagePerformanceDTO fullPagePerformance;
}
