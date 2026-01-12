package com.example.securitytest.constant;

/**
 * Lưu các đường dẫn file key / cert
 */
public final class KeyPathConstant {

    private KeyPathConstant() {
        // Prevent instantiation
    }

    /** Đường dẫn private key RSA */
    public static final String RSA_PRIVATE_KEY_PATH = "keys/private.key";

    /** (Nếu có) Public key RSA */
    public static final String RSA_PUBLIC_KEY_PATH = "keys/public.key";

    /** (Nếu sau này dùng keystore) */
    public static final String AES_KEY_NAME = "AES_KEY";
}
