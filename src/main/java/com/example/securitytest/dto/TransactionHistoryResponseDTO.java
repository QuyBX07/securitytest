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

    // UUID của giao dịch (dùng để định danh giao dịch, KHÔNG nên lộ khi log)
    private UUID transactionId;

    // Số tài khoản sau khi đã được giải mã AES
// Đây là dữ liệu nhạy cảm → cần mask khi log
    private String account;

    // Số tiền ghi nợ (tài khoản nguồn)
    private BigDecimal inDebt;

    // Số tiền ghi có (tài khoản đích)
    private BigDecimal have;

    // Thời điểm phát sinh giao dịch
    private LocalDateTime time;



}
