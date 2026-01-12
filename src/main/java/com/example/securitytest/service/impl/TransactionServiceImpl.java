package com.example.securitytest.service.impl;

import com.example.securitytest.dto.TransactionHistoryRequestDTO;
import com.example.securitytest.dto.TransactionHistoryResponseDTO;
import com.example.securitytest.entity.TransactionHistory;
import com.example.securitytest.mapper.TransactionHistoryMapper;
import com.example.securitytest.repository.TransactionHistoryRepository;
import com.example.securitytest.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionHistoryRepository repository;
    private final String aesKey = System.getenv("AES_KEY");

    @Override
    @Transactional
    public List<TransactionHistoryResponseDTO> createTransaction(TransactionHistoryRequestDTO requestDTO) {
        try {
            List<TransactionHistory> entities = TransactionHistoryMapper.toEntities(requestDTO, aesKey);

            // Insert tất cả cùng lúc
            repository.saveAll(entities);

            return entities.stream()
                    .map(e -> TransactionHistoryMapper.toDTO(e, true, aesKey))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error creating transaction: SourceAccount=?, TargetAccount=?, Amount=?", e);
            throw new RuntimeException("Failed to create transaction", e);
        }
    }



    @Override
    public List<TransactionHistoryResponseDTO> getAllTransactions(boolean maskSensitive) {
        return repository.findAll().stream()
                .map(e -> TransactionHistoryMapper.toDTO(e, maskSensitive, aesKey))
                .collect(Collectors.toList());
    }
}
