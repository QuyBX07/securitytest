package com.example.securitytest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
/**
 * Object chuẩn dùng để trả response lỗi cho client.
 */
public class ErrorResponse {
    private String message;
}
