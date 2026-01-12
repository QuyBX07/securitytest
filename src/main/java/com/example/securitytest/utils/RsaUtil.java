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
 * Utility class cho các thao tác RSA:
 * - Mã hóa / giải mã dữ liệu (encrypt/decrypt)
 * - Ký số (sign)
 * - Xác thực chữ ký (verify)
 *
 * Key được load từ file Base64 (PEM không có header/footer)
 */
public class RsaUtil {

    // Tên thuật toán RSA
    private static final String RSA = "RSA";
    // Thuật toán chữ ký sử dụng SHA-256 + RSA
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /**
     * Load public key từ file Base64
     * @param filePath đường dẫn file chứa public key
     * @return PublicKey để dùng cho mã hóa hoặc verify signature
     * @throws Exception nếu đọc file hoặc parse key lỗi
     */
    public static PublicKey loadPublicKey(String filePath) throws Exception {
        // Đọc toàn bộ file Base64
        byte[] keyBytes = Base64.getDecoder().decode(Files.readAllBytes(Path.of(filePath)));
        // Khởi tạo KeyFactory cho RSA
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        // Sinh PublicKey từ X509EncodedKeySpec
        return keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
    }

    /**
     * Load private key từ file Base64
     * @param filePath đường dẫn file chứa private key
     * @return PrivateKey để giải mã hoặc ký dữ liệu
     * @throws Exception nếu đọc file hoặc parse key lỗi
     */
    public static PrivateKey loadPrivateKey(String filePath) throws Exception {
        // Đọc toàn bộ file Base64
        byte[] keyBytes = Base64.getDecoder().decode(Files.readAllBytes(Path.of(filePath)));
        // Khởi tạo KeyFactory cho RSA
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        // Sinh PrivateKey từ PKCS8EncodedKeySpec
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
    }

    /**
     * Mã hóa dữ liệu bằng public key (RSA)
     * @param plainText dữ liệu gốc cần mã hóa
     * @param publicKey public key để mã hóa
     * @return chuỗi Base64 của dữ liệu đã mã hóa
     * @throws NoSuchPaddingException nếu thuật toán padding không tồn tại
     * @throws NoSuchAlgorithmException nếu thuật toán RSA không tồn tại
     * @throws InvalidKeyException nếu key không hợp lệ
     * @throws IllegalBlockSizeException nếu kích thước dữ liệu quá lớn
     * @throws BadPaddingException nếu padding không đúng
     */
    public static String encrypt(String plainText, PublicKey publicKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(RSA); // Sử dụng mặc định RSA/ECB/PKCS1Padding
        cipher.init(Cipher.ENCRYPT_MODE, publicKey); // Init chế độ encrypt
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * Giải mã dữ liệu bằng private key (RSA)
     * @param cipherText dữ liệu đã mã hóa Base64
     * @param privateKey private key để giải mã
     * @return dữ liệu gốc
     * @throws NoSuchPaddingException nếu thuật toán padding không tồn tại
     * @throws NoSuchAlgorithmException nếu thuật toán RSA không tồn tại
     * @throws InvalidKeyException nếu key không hợp lệ
     * @throws IllegalBlockSizeException nếu dữ liệu lỗi block size
     * @throws BadPaddingException nếu dữ liệu bị thay đổi hoặc key sai
     */
    public static String decrypt(String cipherText, PrivateKey privateKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(RSA); // Mặc định RSA/ECB/PKCS1Padding
        cipher.init(Cipher.DECRYPT_MODE, privateKey); // Init chế độ decrypt
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * Ký dữ liệu bằng private key (RSA + SHA-256)
     * @param data dữ liệu gốc (plain text)
     * @param privateKey private key để ký
     * @return chuỗi Base64 chữ ký số
     * @throws NoSuchAlgorithmException nếu thuật toán SHA256withRSA không tồn tại
     * @throws InvalidKeyException nếu key không hợp lệ
     * @throws SignatureException nếu ký thất bại
     */
    public static String sign(String data, PrivateKey privateKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    /**
     * Xác thực chữ ký bằng public key
     * @param data dữ liệu gốc (plain text)
     * @param signatureBase64 chữ ký Base64 cần verify
     * @param publicKey public key để verify
     * @return true nếu chữ ký hợp lệ, false nếu không hợp lệ
     * @throws NoSuchAlgorithmException nếu thuật toán SHA256withRSA không tồn tại
     * @throws InvalidKeyException nếu key không hợp lệ
     * @throws SignatureException nếu xác thực lỗi
     */
    public static boolean verify(String data, String signatureBase64, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        return signature.verify(Base64.getDecoder().decode(signatureBase64));
    }
}
