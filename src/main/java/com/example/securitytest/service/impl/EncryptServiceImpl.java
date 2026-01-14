package com.example.securitytest.service.impl;

import com.example.securitytest.constant.KeyPathConstant;
import com.example.securitytest.service.EncryptService;
import com.example.securitytest.utils.AesUtil;
import com.example.securitytest.utils.RsaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

import static com.example.securitytest.constant.ErrorConstant.*;
import static com.example.securitytest.constant.KeyPathConstant.AES_KEY_NAME;
import static com.example.securitytest.constant.LogMessageConstant.*;

/**
 * Implementation của CryptoService.
 *
 * Class này chịu trách nhiệm xử lý các nghiệp vụ mã hóa / giải mã:
 * - AES: dùng để mã hóa dữ liệu nhạy cảm trước khi lưu database
 * - RSA: dùng để giải mã dữ liệu client gửi lên (đã được mã hóa bằng public key)
 *
 * Lưu ý về bảo mật:
 * - AES key được lấy từ biến môi trường (KHÔNG hard-code)
 * - RSA private key được load từ file nội bộ của hệ thống
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EncryptServiceImpl implements EncryptService {

    /**
     * AES key lấy từ biến môi trường.
     * Dùng để mã hóa / giải mã dữ liệu nhạy cảm (ví dụ: số tài khoản).
     */
    private final String aesKey = System.getenv(AES_KEY_NAME);

    /**
     * Đường dẫn tới RSA private key.
     * Private key chỉ tồn tại ở server, không bao giờ public ra ngoài.
     */

    /**
     * Mã hóa dữ liệu bằng thuật toán AES.
     *
     * @param plainText dữ liệu gốc (chưa mã hóa)
     * @return chuỗi đã được mã hóa AES
     * @throws RuntimeException nếu quá trình mã hóa thất bại
     */
    @Override
    public String encryptAES(String plainText) {
        try {
            return AesUtil.encrypt(plainText, aesKey);
        } catch (Exception e) {
            log.error(LOG_AES_ENCRYPTION_ERROR, e);
            throw new RuntimeException(AES_ENCRYPTION_FAILED);
        }
    }

    /**
     * Giải mã dữ liệu AES.
     *
     * @param cipherText dữ liệu đã được mã hóa AES
     * @return dữ liệu gốc sau khi giải mã
     * @throws RuntimeException nếu quá trình giải mã thất bại
     */
    @Override
    public String decryptAES(String cipherText) {
        try {
            return AesUtil.decrypt(cipherText, aesKey);
        } catch (Exception e) {
            log.error(LOG_AES_DECRYPTION_ERROR, e);
            throw new RuntimeException(AES_DECRYPTION_FAILED);
        }
    }

    /**
     * Giải mã dữ liệu RSA.
     *
     * Thường dùng để:
     * - Giải mã dữ liệu client gửi lên (đã mã hóa bằng RSA public key)
     *
     * @param cipherText dữ liệu đã được mã hóa RSA
     * @return dữ liệu gốc sau khi giải mã
     * @throws IllegalArgumentException nếu dữ liệu không hợp lệ hoặc giải mã thất bại
     */
    @Override
    public String decryptRSA(String cipherText) {
        try {
            PrivateKey privateKey =
                    RsaUtil.loadPrivateKey(KeyPathConstant.RSA_PRIVATE_KEY_PATH);
            return RsaUtil.decrypt(cipherText, privateKey);
        } catch (Exception e) {
            log.error(LOG_RSA_DECRYPTION_ERROR, e);
            throw new IllegalArgumentException(RSA_DECRYPTION_FAILED);
        }
    }

    /**
     * Mã hóa dữ liệu bằng RSA (public key).
     *
     * Thường dùng để:
     * - Mã hóa dữ liệu trả về client
     * - Đảm bảo dữ liệu response không bị đọc lén
     *
     * @param plainText dữ liệu gốc
     * @return dữ liệu đã được mã hóa RSA
     * @throws RuntimeException nếu mã hóa thất bại
     */
    @Override
    public String encryptRSA(String plainText) {
        try {
            return RsaUtil.encrypt(
                    plainText,
                    RsaUtil.loadPublicKey(
                            KeyPathConstant.RSA_PUBLIC_KEY_PATH
                    )
            );
        } catch (Exception e) {
            log.error(LOG_RSA_ENCRYPTION_ERROR, e);
            throw new RuntimeException(RSA_ENCRYPTION_FAILED);
        }
    }
}
