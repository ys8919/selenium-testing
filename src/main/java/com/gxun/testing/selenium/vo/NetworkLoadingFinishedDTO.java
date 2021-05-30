package com.gxun.testing.selenium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetworkLoadingFinishedDTO {
    private String requestId;
    private Double timestamp;
    private Long encodedDataLength;
    private Number blockedCrossSiteDocument;

}
