package com.example.securitytest.service;

/**
 * Service cung cấp các phương thức mã hóa và giải mã dữ liệu.
 */
public interface EncryptService {

    /**
     * Mã hóa dữ liệu bằng thuật toán AES.
     *
     * <p>Dùng để mã hóa các thông tin nhạy cảm
     * (ví dụ: số tài khoản, thông tin định danh)
     * trước khi lưu xuống database.</p>
     *
     * @param plainText dữ liệu gốc (chưa mã hóa)
     * @return chuỗi đã được mã hóa bằng AES
     * @throws RuntimeException nếu quá trình mã hóa thất bại
     */
    String encryptAES(String plainText);

    /**
     * Mã hóa dữ liệu bằng thuật toán RSA với public key.
     *
     * <p>Dùng để mã hóa các dữ liệu cần bảo mật
     * khi truyền qua mạng (ví dụ: mật khẩu, token).
     * Chỉ người sở hữu private key mới có thể giải mã.</p>
     *
     * @param cipherText dữ liệu gốc (chưa mã hóa)
     * @return chuỗi đã được mã hóa bằng RSA
     * @throws RuntimeException nếu quá trình mã hóa thất bại
     */
    String decryptAES(String cipherText);

    /**
     * Giải mã dữ liệu đã mã hóa bằng RSA với private key.
     *
     * <p>Dùng để giải mã các dữ liệu nhạy cảm
     * sau khi nhận từ client gửi lên.</p>
     *
     * @param cipherText dữ liệu đã mã hóa
     * @return chuỗi đã được giải mã
     * @throws RuntimeException nếu quá trình giải mã thất bại
     */
    String decryptRSA(String cipherText);

    /**
     * Mã hóa dữ liệu bằng thuật toán RSA với public key.
     *
     * <p>Dùng để mã hóa các dữ liệu cần bảo mật
     * khi truyền qua mạng (ví dụ: mật khẩu, token).
     * Chỉ người sở hữu private key mới có thể giải mã.</p>
     *
     * @param plainText dữ liệu gốc (chưa mã hóa)
     * @return chuỗi đã được mã hóa bằng RSA
     * @throws RuntimeException nếu quá trình mã hóa thất bại
     */
    String encryptRSA(String plainText);

}
