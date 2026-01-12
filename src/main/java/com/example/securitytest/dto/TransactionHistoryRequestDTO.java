package com.example.securitytest.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO nhận dữ liệu giao dịch chuyển khoản từ client hoặc service khác
 * Các field này sẽ được client/service mã hóa RSA trước khi gửi
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionHistoryRequestDTO {

    private String sourceAccount; // RSA encrypted
    private String targetAccount; // RSA encrypted
    private String amount;      // RSA encrypted
}
