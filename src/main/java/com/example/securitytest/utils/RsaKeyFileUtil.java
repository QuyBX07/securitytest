package com.example.securitytest.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.util.Base64;

/**
 * Utility quản lý key RSA lưu file
 */
public class RsaKeyFileUtil {

    private static final String RSA = "RSA";
    private static final int RSA_KEY_SIZE = 2048;

    /**
     * Sinh cặp key và lưu ra file
     */
    public static void generateKeyFiles(String publicKeyFile, String privateKeyFile) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(RSA);
        keyGen.initialize(RSA_KEY_SIZE);
        KeyPair keyPair = keyGen.generateKeyPair();

        // Lưu public key
        try (FileWriter pubOut = new FileWriter(publicKeyFile)) {
            pubOut.write(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        }

        // Lưu private key
        try (FileWriter privOut = new FileWriter(privateKeyFile)) {
            privOut.write(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        }
    }

    /**
     * Load public key từ file
     */
    public static PublicKey loadPublicKey(String filePath) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(Files.readAllBytes(Path.of(filePath)));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(new java.security.spec.X509EncodedKeySpec(keyBytes));
    }

    /**
     * Load private key từ file
     */
    public static PrivateKey loadPrivateKey(String filePath) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(Files.readAllBytes(Path.of(filePath)));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(keyBytes));
    }

    public static void main(String[] args) throws Exception {
        // Sinh key và lưu tạm trong project
        generateKeyFiles("public.key", "private.key");
        System.out.println("Keys generated!");
    }
}
