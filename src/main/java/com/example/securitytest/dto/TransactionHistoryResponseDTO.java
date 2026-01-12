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

    private UUID transactionId; // UUID
    private String account;       // Số tài khoản (giải mã AES)
    private BigDecimal inDebt;
    private BigDecimal have;
    private LocalDateTime time;

    /**
     * Mask các dữ liệu nhạy cảm khi log
     */
    public void maskSensitiveData() {
        this.transactionId = null;
        this.account = mask(this.account);
        this.inDebt = null;
        this.have = null;
        this.time = null;
    }

    private String mask(String value) {
        if (value == null) return null;
        return "?".repeat(value.length());
    }
}
