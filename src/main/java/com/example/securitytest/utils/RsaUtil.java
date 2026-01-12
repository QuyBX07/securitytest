package com.example.securitytest.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RsaUtil
 *
 * Utility RSA, sử dụng key load từ file.
 */
public class RsaUtil {

    private static final String RSA = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /** Load public key từ file Base64 */
    public static PublicKey loadPublicKey(String filePath) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(Files.readAllBytes(Path.of(filePath)));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
    }

    /** Load private key từ file Base64 */
    public static PrivateKey loadPrivateKey(String filePath) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(Files.readAllBytes(Path.of(filePath)));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
    }

    /** Mã hóa bằng public key */
    public static String encrypt(String plainText, PublicKey publicKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /** Giải mã bằng private key */
    public static String decrypt(String cipherText, PrivateKey privateKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /** Ký dữ liệu bằng private key */
    public static String sign(String data, PrivateKey privateKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    /** Verify chữ ký bằng public key */
    public static boolean verify(String data, String signatureBase64, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        return signature.verify(Base64.getDecoder().decode(signatureBase64));
    }
}
