package com.example.securitytest.logging;

/**
 * Chứa các message log có dữ liệu nhạy cảm đã được mask
 */
public final class TransactionLogMessage {

    private TransactionLogMessage() {}

    // ===== ẩn message cho các transactions =====
    public static final String MASKED_TRANSACTION =
        "sourceAccount=?, targetAccount=?, amount=?";
}
