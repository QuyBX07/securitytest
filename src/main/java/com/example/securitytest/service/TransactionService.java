package com.example.securitytest.service;

import com.example.securitytest.dto.TransactionHistoryRequestDTO;
import com.example.securitytest.dto.TransactionHistoryResponseDTO;

import java.util.List;

/**
 * TransactionService
 *
 * Định nghĩa các nghiệp vụ liên quan đến giao dịch.
 * Interface này được Controller sử dụng và
 * được implement bởi lớp TransactionServiceImpl.
 *
 * Mục đích:
 *  - Tách biệt tầng Controller và tầng xử lý nghiệp vụ
 *  - Dễ mở rộng, dễ test, dễ thay đổi implementation
 */
public interface TransactionService {

    /**
     * Tạo một giao dịch chuyển tiền.
     *
     * @param requestDTO dữ liệu yêu cầu tạo giao dịch từ client
     *                   (đã được mã hóa RSA phía client)
     * @return danh sách TransactionHistoryResponseDTO gồm 2 phần tử
     *         (debit và credit) sau khi xử lý thành công
     * @throws IllegalArgumentException nếu dữ liệu giao dịch không hợp lệ
     * @throws RuntimeException nếu xảy ra lỗi hệ thống trong quá trình tạo giao dịch
     */
    List<TransactionHistoryResponseDTO> createTransaction(TransactionHistoryRequestDTO requestDTO);

    /**
     * Lấy danh sách toàn bộ lịch sử giao dịch.
     *
     * <p>
     * Dữ liệu trả về đã được:
     * <ul>
     *   <li>Giải mã AES đối với các trường nhạy cảm</li>
     *   <li>Map từ Entity sang DTO trước khi trả về client</li>
     * </ul>
     * </p>
     *
     * @return danh sách TransactionHistoryResponseDTO
     *         (rỗng nếu không có giao dịch nào)
     */
    List<TransactionHistoryResponseDTO> getAllTransactions();
}
