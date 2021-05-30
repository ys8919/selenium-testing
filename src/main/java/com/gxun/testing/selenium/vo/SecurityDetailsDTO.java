package com.gxun.testing.selenium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityDetailsDTO {
    private String protocol;
    private String keyExchange;
    private String keyExchangeGroup;
    private String cipher;
    private String mac;
    private Integer certificateId;
    private String subjectName;
    private String[] sanList;
    private String issuer;
    private Long validFrom;
    private Long validTo;
    private String signedCertificateTimestampList;
}
