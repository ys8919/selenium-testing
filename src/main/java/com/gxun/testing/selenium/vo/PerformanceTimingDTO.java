package com.gxun.testing.selenium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceTimingDTO {
    private Double domContentLoadedEventEnd;
    private Double connectStart;
    private Double domContentLoadedCost;
    private Double loadEventEnd;
}
