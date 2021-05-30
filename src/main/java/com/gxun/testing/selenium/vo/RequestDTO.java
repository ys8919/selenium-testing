package com.gxun.testing.selenium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    private String url;
    private String method;
    private String headers;
    private String postData;
    private Boolean hasPostData;
    private String mixedContentType;
    private String initialPriority;
    private String referrerPolicy;
    private Boolean isLinkPreload;
}
