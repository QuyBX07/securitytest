package com.example.securitytest.mapper;

import com.example.securitytest.dto.TransactionHistoryResponseDTO;
import com.example.securitytest.entity.TransactionHistory;
import com.example.securitytest.service.EncryptService;

/**
 * Mapper chuyển đổi giữa DTO và Entity cho TransactionHistory
 */
public class TransactionHistoryMapper {

    /**
     * Chuyển đổi TransactionHistory Entity sang TransactionHistoryResponseDTO.
     *
     * Quy trình:
     * 1. Giải mã số tài khoản từ AES trước khi trả về DTO
     * 2. Map toàn bộ dữ liệu từ Entity sang DTO
     *
     * @param entity        entity lịch sử giao dịch lấy từ database
     * @param encryptService service thực hiện giải mã AES
     * @return TransactionHistoryResponseDTO đã được map (hoặc null nếu entity null)
     */
    public static TransactionHistoryResponseDTO toDTO(
            TransactionHistory entity,
            EncryptService encryptService
    ) {
        // Trường hợp entity null thì không map, tránh NullPointerException
        if (entity == null) return null;

        // Giải mã số tài khoản (được mã hóa AES trong database)
        String accountPlain = encryptService.decryptAES(entity.getAccount());

        // Build DTO từ dữ liệu entity
        return TransactionHistoryResponseDTO.builder()
                .transactionId(
                        encryptService.encryptRSA(entity.getTransactionId().toString())
                )
                .account(
                        encryptService.encryptRSA(accountPlain)
                )
                .inDebt(
                        encryptService.encryptRSA(entity.getInDebt().toPlainString())
                )
                .have(
                        encryptService.encryptRSA(entity.getHave().toPlainString())
                )
                .time(
                        encryptService.encryptRSA(entity.getTime().toString())
                )
                .build();
    }
}

