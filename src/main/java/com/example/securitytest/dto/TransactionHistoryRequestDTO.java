package com.example.securitytest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;


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
    @NotBlank(message = "Số tài khoản nguồn không được để trống")
    private String sourceAccount; // RSA encrypted

    @NotBlank(message = "Số tài khoản đích không được để trống")
    private String targetAccount; // RSA encrypted

    @NotBlank(message = "Số tiền không được để trống")
    private String amount;      // RSA encrypted
}
