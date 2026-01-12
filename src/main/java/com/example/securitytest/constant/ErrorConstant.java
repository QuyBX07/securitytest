package com.example.securitytest.constant;

/**
 * Chứa các message lỗi dùng chung trong hệ thống
 */
public final class ErrorConstant {

    private ErrorConstant() {
        // prevent instantiation
    }

    public static final String INVALID_AMOUNT =
            "Số tiền không hợp lệ";

    public static final String INVALID_TRANSACTION_DATA =
            "Dữ liệu giao dịch không hợp lệ";

    public static final String CREATE_TRANSACTION_FAILED =
            "Tạo giao dịch thất bại";

    public static final String AES_ENCRYPTION_FAILED =
            "AES encryption failed";

    public static final String AES_DECRYPTION_FAILED =
            "AES decryption failed";

    public static final String RSA_DECRYPTION_FAILED =
            "RSA decryption failed";
}
