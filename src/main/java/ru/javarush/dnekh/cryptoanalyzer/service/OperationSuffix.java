package ru.javarush.dnekh.cryptoanalyzer.service;

/**
 * Enum representing suffixes for different file operations.
 */
public enum OperationSuffix {
    ENCRYPT("_encrypted"),
    DECRYPT("_decrypted"),
    BRUTE_FORCE_DECRYPT("_bruteforce_decrypted");

    private final String suffix;

    OperationSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
}