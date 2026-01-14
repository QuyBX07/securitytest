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

    /** Đường Public key RSA */
    public static final String RSA_PUBLIC_KEY_PATH = "keys/public.key";

    /** tên biến môi trường AES key */
    public static final String AES_KEY_NAME = "AES_KEY";
}
