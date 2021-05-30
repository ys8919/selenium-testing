package com.gxun.testing.selenium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceTimingDTO {
    private  Double requestTime;
    private  Double proxyStart;
    private  Double proxyEnd;
    private  Double dnsStart;
    private  Double dnsEnd;
    private  Double connectStart;
    private  Double connectEnd;
    private  Double sslStart;
    private  Double sslEnd;
    private  Double workerStart;
    private  Double workerReady;
    private  Double sendStart;
    private  Double sendEnd;
    private  Double pushStart;
    private  Double pushEnd;
    private  Double receiveHeadersEnd;
}
