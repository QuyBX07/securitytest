package com.example.securitytest.controller;

import com.example.securitytest.dto.TransactionHistoryRequestDTO;
import com.example.securitytest.dto.TransactionHistoryResponseDTO;
import com.example.securitytest.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Tạo giao dịch mới
     */
    @PostMapping
    public ResponseEntity<List<TransactionHistoryResponseDTO>> createTransaction(
            @RequestBody TransactionHistoryRequestDTO requestDTO
    ) {
        List<TransactionHistoryResponseDTO> responses =
                transactionService.createTransaction(requestDTO);
        return ResponseEntity.ok(responses);
    }

    /**
     * Lấy tất cả giao dịch
     */
    @GetMapping
    public ResponseEntity<List<TransactionHistoryResponseDTO>> getAllTransactions(
            @RequestParam(defaultValue = "true") boolean maskSensitive
    ) {
        List<TransactionHistoryResponseDTO> responses =
                transactionService.getAllTransactions(maskSensitive);
        return ResponseEntity.ok(responses);
    }
}
