package com.gxun.testing.selenium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor@NoArgsConstructor
public class NetworkRequestWillBeSentDTO {
    private String requestId;
    private String loaderId;
    private String documentURL;
    private Double timestamp;
    private Long wallTime;
    private String type;
    private String frameId;
    private RequestDTO request;
    private ResponseDTO redirectResponse;

}
