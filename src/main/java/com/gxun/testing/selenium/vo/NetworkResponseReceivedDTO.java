package com.gxun.testing.selenium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetworkResponseReceivedDTO {
    private String requestId;
    private String loaderId;
    private Double timestamp;
    private String type;
    private ResponseDTO response;
    private String frameId;
}
