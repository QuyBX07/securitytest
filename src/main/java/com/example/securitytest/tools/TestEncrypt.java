package com.example.securitytest.tools;

import com.example.securitytest.constant.KeyPathConstant;
import com.example.securitytest.utils.RsaUtil;
import java.security.PublicKey;

public class TestEncrypt {
    public static void main(String[] args) throws Exception {
        // Load public key
        String publicKeyPath = KeyPathConstant.RSA_PUBLIC_KEY_PATH;
        PublicKey publicKey = RsaUtil.loadPublicKey(publicKeyPath);

        // Dữ liệu gốc
        String sourceAccount = "123456789";
        String targetAccount = "987654321";
        String amount = "100000"; // string vì RSA chỉ mã hóa chuỗi

        // Mã hóa RSA
        String encryptedSource = RsaUtil.encrypt(sourceAccount, publicKey);
        String encryptedTarget = RsaUtil.encrypt(targetAccount, publicKey);
        String encryptedAmount = RsaUtil.encrypt(amount, publicKey);

        System.out.println("sourceAccount:" + encryptedSource);
        System.out.println("targetAccount" + encryptedTarget);
        System.out.println("amount" + encryptedAmount);
    }
}
