package com.example.securitytest.constant;

/**
 * Lưu message log (debug / warn / error)
 */
public final class LogMessageConstant {

    private LogMessageConstant() {}

    // ===== Transaction =====

    public static final String LOG_INVALID_AMOUNT =
            "Dữ liệu số không hợp lệ trong yêu cầu giao dịch: {}";

    public static final String LOG_INVALID_TRANSACTION_DATA =
            "Yêu cầu giao dịch không hợp lệ: {}";

    public static final String LOG_CREATE_TRANSACTION_ERROR =
            "Lỗi hệ thống khi tạo giao dịch: {}";

    public static final String LOG_AES_ENCRYPTION_ERROR =
            "AES encryption error";

    public static final String LOG_AES_DECRYPTION_ERROR =
            "AES decryption error";

    public static final String LOG_RSA_DECRYPTION_ERROR =
            "RSA decryption error";

    public static final String LOG_RSA_ENCRYPTION_ERROR =
            "RSA encryption error";
}
