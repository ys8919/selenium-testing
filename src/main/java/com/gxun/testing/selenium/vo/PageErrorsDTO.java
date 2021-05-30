package com.gxun.testing.selenium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageErrorsDTO {
    private List<CodeErrorResponseDTO> codeErrorResponseList;
    private List<FailResponseDTO> failResponseList;
    private List<SlowReponseDTO> slowReponseList;
}
