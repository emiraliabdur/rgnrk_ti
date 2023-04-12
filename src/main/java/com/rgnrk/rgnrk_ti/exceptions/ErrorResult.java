package com.rgnrk.rgnrk_ti.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResult {
    private String message;
    private String requestDescription;
}
