package com.example.securitytest.service;

import com.example.securitytest.dto.TransactionHistoryRequestDTO;
import com.example.securitytest.dto.TransactionHistoryResponseDTO;

import java.util.List;

public interface TransactionService {

    /**
     * Tạo giao dịch (sinh 2 bản ghi: debit + credit)
     */
    List<TransactionHistoryResponseDTO> createTransaction(TransactionHistoryRequestDTO requestDTO);

    /**
     * Lấy tất cả giao dịch
     */
    List<TransactionHistoryResponseDTO> getAllTransactions(boolean maskSensitive);
}
