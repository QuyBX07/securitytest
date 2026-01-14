package com.example.securitytest.utils;

public class LogMaskUtil {

    public static String mask(String value) {
        return value == null ? "?" : "????";
    }

    public static String maskedTransactionLog() {
        return "transactionId=?, account=?, inDebt=?, have=?, time=?";
    }
}
