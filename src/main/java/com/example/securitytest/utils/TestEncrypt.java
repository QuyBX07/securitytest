package com.example.securitytest.utils;

import com.example.securitytest.utils.RsaUtil;
import java.security.PublicKey;

public class TestEncrypt {
    public static void main(String[] args) throws Exception {
        // Load public key
        String publicKeyPath = "keys/public.key";
        PublicKey publicKey = RsaUtil.loadPublicKey(publicKeyPath);

        // Dữ liệu gốc
        String sourceAccount = "123456789";
        String targetAccount = "987654321";
        String amount = "100000"; // string vì RSA chỉ mã hóa chuỗi

        // Mã hóa RSA
        String encryptedSource = RsaUtil.encrypt(sourceAccount, publicKey);
        String encryptedTarget = RsaUtil.encrypt(targetAccount, publicKey);
        String encryptedAmount = RsaUtil.encrypt(amount, publicKey);

        System.out.println("Encrypted source: " + encryptedSource);
        System.out.println("Encrypted target: " + encryptedTarget);
        System.out.println("Encrypted amount: " + encryptedAmount);
    }
}
