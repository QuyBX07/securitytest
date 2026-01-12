package com.example.securitytest.controller;

import com.example.securitytest.dto.TransactionHistoryRequestDTO;
import com.example.securitytest.dto.TransactionHistoryResponseDTO;
import com.example.securitytest.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
/* Controller xử lý các API liên quan đến giao dịch
 */
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Tạo giao dịch mới
     */
    @PostMapping
    public ResponseEntity<List<TransactionHistoryResponseDTO>> createTransaction(
            @RequestBody @Valid TransactionHistoryRequestDTO requestDTO
    ) {
        List<TransactionHistoryResponseDTO> responses = transactionService.createTransaction(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED) // 201 Created
                .body(responses);
    }


    /**
     * Lấy tất cả giao dịch
     */
    @GetMapping
    public ResponseEntity<List<TransactionHistoryResponseDTO>> getAllTransactions(
    ) {
        List<TransactionHistoryResponseDTO> responses =
                transactionService.getAllTransactions();
        return ResponseEntity.ok(responses);
    }
}
