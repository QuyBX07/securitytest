package com.example.securitytest.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * AesCryptoUtil
 *
 * Utility class dùng để:
 * - Mã hóa (encrypt)
 * - Giải mã (decrypt)
 * dữ liệu bằng thuật toán AES (mã hóa đối xứng).
 *
 * Đặc điểm:
 * - Thuật toán: AES
 * - Kiểu mã hóa: đối xứng (encrypt & decrypt dùng cùng 1 key)
 * - Encoding output: Base64 (phục vụ lưu DB / truyền HTTP)
 *
 * Lưu ý:
 * - Key AES KHÔNG được hardcode trong code
 * - Base64 KHÔNG phải mã hóa, chỉ là encoding
 */
public class AesUtil {

    /**
     * Tên thuật toán mã hóa.
     *
     * Khi chỉ định "AES", JVM sẽ dùng mặc định:
     * AES/ECB/PKCS5Padding
     *
     * ⚠ ECB không an toàn cho production (chỉ dùng demo / học tập)
     */
    private static final String ALGORITHM = "AES";

    /**
     * Mã hóa dữ liệu plain text bằng AES.
     *
     * @param plainText dữ liệu gốc (chưa mã hóa)
     * @param secretKey khóa AES
     *                  (độ dài hợp lệ: 16 / 24 / 32 bytes)
     * @return chuỗi đã mã hóa và encode Base64
     *
     * @throws NoSuchPaddingException    thuật toán padding không tồn tại
     * @throws NoSuchAlgorithmException thuật toán AES không tồn tại
     * @throws InvalidKeyException      key không hợp lệ
     * @throws IllegalBlockSizeException lỗi kích thước block
     * @throws BadPaddingException      lỗi padding khi mã hóa
     */
    public static String encrypt(String plainText, String secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {

       /* Tạo SecretKeySpec từ chuỗi secretKey */
        SecretKeySpec keySpec =
                new SecretKeySpec(secretKey.getBytes(), ALGORITHM);

      /* Khởi tạo Cipher với thuật toán AES */
        Cipher cipher = Cipher.getInstance(ALGORITHM);

       /* Khởi tạo Cipher ở chế độ ENCRYPT */
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

       /* Thực hiện mã hóa */
        byte[] encryptedBytes =
                cipher.doFinal(plainText.getBytes());

        /* Encode kết quả mã hóa sang Base64 */
        return Base64
                .getEncoder()
                .encodeToString(encryptedBytes);
    }

    /**
     * Giải mã dữ liệu đã mã hóa bằng AES.
     *
     * @param cipherText dữ liệu đã mã hóa (Base64)
     * @param secretKey  khóa AES (phải giống key dùng khi encrypt)
     * @return dữ liệu gốc (plain text)
     *
     * @throws NoSuchPaddingException    padding không tồn tại
     * @throws NoSuchAlgorithmException thuật toán AES không tồn tại
     * @throws InvalidKeyException      key sai hoặc không hợp lệ
     * @throws IllegalBlockSizeException lỗi block size
     * @throws BadPaddingException      sai key hoặc dữ liệu bị thay đổi
     */
    public static String decrypt(String cipherText, String secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {

        /**
         * Tạo lại SecretKeySpec từ secretKey.
         * Key phải giống key lúc encrypt.
         */
        SecretKeySpec keySpec =
                new SecretKeySpec(secretKey.getBytes(), ALGORITHM);

        /**
         * Khởi tạo Cipher với thuật toán AES.
         */
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        /**
         * Khởi tạo Cipher ở chế độ DECRYPT.
         */
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        /**
         * Decode chuỗi Base64 về mảng byte đã mã hóa,
         * sau đó thực hiện giải mã.
         */
        byte[] decryptedBytes =
                cipher.doFinal(
                        Base64.getDecoder().decode(cipherText)
                );

        /**
         * Convert mảng byte đã giải mã
         * về chuỗi plain text ban đầu.
         */
        return new String(decryptedBytes);
    }
}
