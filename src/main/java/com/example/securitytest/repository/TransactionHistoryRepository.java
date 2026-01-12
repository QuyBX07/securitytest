package com.example.securitytest.repository;

import com.example.securitytest.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
/**
 * Repository cho entity TransactionHistory.
 *
 * Cung cấp các phương thức truy xuất dữ liệu liên quan đến lịch sử giao dịch.
 */
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, UUID> {

    /**
     * Tìm tất cả giao dịch theo transactionId
     */
    List<TransactionHistory> findByTransactionId(String transactionId);

    /**
     * Tìm tất cả giao dịch theo account (AES encrypted)
     */
    List<TransactionHistory> findByAccount(String encryptedAccount);

    /**
     * Xóa tất cả giao dịch theo transactionId
     */
    void deleteByTransactionId(String transactionId);
}
