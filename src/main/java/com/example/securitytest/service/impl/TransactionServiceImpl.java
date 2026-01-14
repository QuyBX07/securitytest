package com.example.securitytest.service.impl;


import com.example.securitytest.dto.TransactionHistoryRequestDTO;
import com.example.securitytest.dto.TransactionHistoryResponseDTO;
import com.example.securitytest.entity.TransactionHistory;
import com.example.securitytest.logging.TransactionLogMessage;
import com.example.securitytest.mapper.TransactionHistoryMapper;
import com.example.securitytest.repository.TransactionHistoryRepository;
import com.example.securitytest.service.EncryptService;
import com.example.securitytest.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.securitytest.constant.ErrorConstant.*;
import static com.example.securitytest.constant.LogMessageConstant.*;

/**
 * Service xử lý nghiệp vụ giao dịch
 *
 * - Nhận dữ liệu giao dịch từ client (đã mã hóa RSA)
 * - Giải mã dữ liệu đầu vào
 * - Mã hóa AES trước khi lưu DB
 * - Tạo 2 bản ghi giao dịch: ghi nợ & ghi có
 * - Bắt và phân loại exception để GlobalExceptionHandler xử lý HTTP response
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    /** Repository thao tác với bảng transaction_history */
    private final TransactionHistoryRepository repository;

    /** Service mã hóa / giải mã (RSA + AES) */
    private final EncryptService encryptService;

    /**
     * Tạo giao dịch mới
     *
     * @param requestDTO dữ liệu giao dịch từ client (đã mã hóa RSA)
     * @return danh sách giao dịch sau khi lưu (debit & credit)
     */
    @Override
    @Transactional
    public List<TransactionHistoryResponseDTO> createTransaction(TransactionHistoryRequestDTO requestDTO) {
        try {
            // 1️⃣ Giải mã dữ liệu nhận từ client bằng RSA
            String sourceAccount = encryptService.decryptRSA(requestDTO.getSourceAccount());
            String targetAccount = encryptService.decryptRSA(requestDTO.getTargetAccount());
            BigDecimal amount = new BigDecimal(
                    encryptService.decryptRSA(requestDTO.getAmount())
            );

            // 2️⃣ Mã hóa AES trước khi lưu database
            String encryptedSource = encryptService.encryptAES(sourceAccount);
            String encryptedTarget = encryptService.encryptAES(targetAccount);

            // 3️⃣ Chuẩn bị thông tin giao dịch
            UUID transactionId = UUID.randomUUID();
            LocalDateTime now = LocalDateTime.now();

            // 4️⃣ Bản ghi ghi nợ (debit)
            TransactionHistory debit = TransactionHistory.builder()
                    .transactionId(transactionId)
                    .account(encryptedSource)
                    .inDebt(amount)
                    .have(BigDecimal.ZERO)
                    .time(now)
                    .build();

            // 5️⃣ Bản ghi ghi có (credit)
            TransactionHistory credit = TransactionHistory.builder()
                    .transactionId(transactionId)
                    .account(encryptedTarget)
                    .inDebt(BigDecimal.ZERO)
                    .have(amount)
                    .time(now)
                    .build();

            // 6️⃣ Lưu cả 2 bản ghi trong cùng transaction
            repository.saveAll(List.of(debit, credit));

            // 7️⃣ Chuyển entity → DTO để trả về client
            return List.of(
                    TransactionHistoryMapper.toDTO(debit, encryptService),
                    TransactionHistoryMapper.toDTO(credit, encryptService)
            );

        } catch (NumberFormatException e) {
            log.warn(LOG_INVALID_AMOUNT, TransactionLogMessage.MASKED_TRANSACTION);
            throw new NumberFormatException(INVALID_AMOUNT);

        } catch (IllegalArgumentException e) {
            log.warn(LOG_INVALID_TRANSACTION_DATA, TransactionLogMessage.MASKED_TRANSACTION);
            throw new IllegalArgumentException(INVALID_TRANSACTION_DATA);

        } catch (Exception e) {
            log.error(LOG_CREATE_TRANSACTION_ERROR, TransactionLogMessage.MASKED_TRANSACTION);
            throw new RuntimeException(CREATE_TRANSACTION_FAILED);
        }
    }

    /**
     * Lấy danh sách toàn bộ giao dịch
     *
     * @return danh sách giao dịch
     */
    @Override
    public List<TransactionHistoryResponseDTO> getAllTransactions() {
        return repository.findAll().stream()
                .map(e -> TransactionHistoryMapper.toDTO(e, encryptService))
                .collect(Collectors.toList());
    }
}
