package com.gxun.testing.selenium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetworkLoadingFailedDTO {
    private String requestId;
    private Double timestamp;
    private String type;
    private String errorText;
    private Boolean canceled;
    private String blockedReason;
}
