package com.example.securitytest.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * Entity lưu lịch sử giao dịch chuyển khoản.
 *
 * Mỗi giao dịch phát sinh 2 bản ghi:
 * - Nợ cho tài khoản nguồn
 * - Có cho tài khoản đích
 */
public class TransactionHistory {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    /**
     * Mã giao dịch
     */
    @UuidGenerator
    @Column(name = "transaction_id", nullable = false, length = 100)
    private UUID transactionId;

    /**
     * Số tài khoản (đã mã hóa AES)
     */
    @Column(name = "account", nullable = false, length = 256)
    private String account;

    /**
     * Số tiền nợ (tài khoản nguồn)
     */
    @Column(name = "in_debt", nullable = false)
    private BigDecimal inDebt;

    /**
     * Số tiền có (tài khoản đích)
     */
    @Column(name = "have", nullable = false)
    private BigDecimal have;

    /**
     * Thời gian phát sinh giao dịch
     */
    @Column(name = "time", nullable = false)
    private LocalDateTime time;
}
