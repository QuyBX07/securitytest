package com.example.securitytest.mapper;

import com.example.securitytest.dto.TransactionHistoryRequestDTO;
import com.example.securitytest.dto.TransactionHistoryResponseDTO;
import com.example.securitytest.entity.TransactionHistory;
import com.example.securitytest.utils.AesUtil;
import com.example.securitytest.utils.RsaUtil;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.security.PrivateKey;

/**
 * Mapper chuyển đổi giữa DTO và Entity cho TransactionHistory
 */
public class TransactionHistoryMapper {

    private TransactionHistoryMapper() {} // static only

    private static final String PRIVATE_KEY_PATH = "keys/private.key"; // đường dẫn private key RSA

    /**
     * Chuyển RequestDTO → 2 Entity (debit + credit)
     */
    @SneakyThrows
    public static List<TransactionHistory> toEntities(
            TransactionHistoryRequestDTO dto,
            String aesKey
    ) {
        if (dto == null) return List.of();

        // Load private key RSA từ file
        PrivateKey privateKey = RsaUtil.loadPrivateKey(PRIVATE_KEY_PATH);

        // Sinh UUID cho giao dịch
        UUID transactionIdgenaretd = UUID.randomUUID();

        // Giải mã RSA từ các field đã mã hóa
        String sourceAccount = RsaUtil.decrypt(dto.getSourceAccount(), privateKey);
        String targetAccount = RsaUtil.decrypt(dto.getTargetAccount(), privateKey);
        String decryptedAmountStr = RsaUtil.decrypt(dto.getAmount(), privateKey);
        BigDecimal amount = new BigDecimal(decryptedAmountStr);

        // Mã hóa AES trước khi lưu DB
        String encryptedSource = AesUtil.encrypt(sourceAccount, aesKey);
        String encryptedTarget = AesUtil.encrypt(targetAccount, aesKey);

        LocalDateTime now = LocalDateTime.now();

        TransactionHistory debit = TransactionHistory.builder()
                .transactionId(transactionIdgenaretd) // đây
                .account(encryptedSource)
                .inDebt(amount)
                .have(BigDecimal.ZERO)
                .time(now)
                .build();

        TransactionHistory credit = TransactionHistory.builder()
                .transactionId(transactionIdgenaretd) // vẫn giống debit
                .account(encryptedTarget)
                .inDebt(BigDecimal.ZERO)
                .have(amount)
                .time(now)
                .build();

        return List.of(debit, credit);
    }

    /**
     * Chuyển Entity → ResponseDTO
     */
    @SneakyThrows
    public static TransactionHistoryResponseDTO toDTO(
            TransactionHistory entity,
            boolean maskSensitive,
            String aesKey
    ) {
        if (entity == null) return null;

        String account = AesUtil.decrypt(entity.getAccount(), aesKey);

        TransactionHistoryResponseDTO dto = TransactionHistoryResponseDTO.builder()
                .transactionId(entity.getTransactionId())
                .account(account)
                .inDebt(entity.getInDebt())
                .have(entity.getHave())
                .time(entity.getTime())
                .build();

        if (maskSensitive) {
            dto.maskSensitiveData();
        }

        return dto;
    }
}
