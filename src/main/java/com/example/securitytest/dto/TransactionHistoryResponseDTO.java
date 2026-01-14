package com.example.securitytest.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO trả dữ liệu giao dịch cho client
 * Có thể mask dữ liệu nhạy cảm khi log
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionHistoryResponseDTO {

    private String transactionId; // RSA string

    private String account;        // RSA string

    private String inDebt;         // RSA string

    private String have;           // RSA string

    private String time;           // RSA string
}
