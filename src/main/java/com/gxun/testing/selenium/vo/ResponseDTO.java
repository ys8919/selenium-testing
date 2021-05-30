package com.gxun.testing.selenium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private String url;
    private Integer status;
    private String statusText;
    private String headers;
    private String headersText;
    private String mimeType;
    private String requestHeaders;
    private String requestHeadersText;
    private Boolean connectionReused;
    private Integer connectionId;
    private String remotelPAddress;
    private Integer remotePort;
    private Boolean fromDiskCache;
    private Boolean fromServiceWorker;
    private Long encodedDataLength;
    private ResourceTimingDTO timing;
    private String protocol;
    private String securityState;
    private SecurityDetailsDTO securityDetails;
}
