package com.gxun.testing.selenium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignedCertificateTimestampDTO {
    private String status;
    private String origin;
    private String logDescription;
    private String logId;
    private Long timestamp;
    private String hashAlgorithm;
    private String signatureAlgorithm;
    private String signatureData;

}
