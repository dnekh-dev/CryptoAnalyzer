package ru.javarush.dnekh.cryptoanalyzer.validation;

import java.io.File;

/**
 * This class provides methods for validating input data such as file paths and directories.
 */
public class InputValidator {

    /**
     * Validates if the specified file path is valid and the file exists.
     *
     * @param filePath the file path to validate
     * @return true if the file exists and can be read, false otherwise
     */
    public static boolean isValidFilePath(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile() && file.canRead();
    }

    /**
     * Validates if the specified directory path is valid and writable.
     * Ensures that the directory is not a system directory.
     *
     * @param directoryPath the directory path to validate
     * @return true if the directory is valid and writable, false otherwise
     */
    public static boolean isValidDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        return directory.exists() && directory.isDirectory() && directory.canWrite() && !isSystemDirectory(directory);
    }

    /**
     * Checks if the specified directory is a system directory.
     *
     * @param directory the directory to check
     * @return true if the directory is a system directory, false otherwise
     */
    private static boolean isSystemDirectory(File directory) {
        String[] systemDirectories = {
                "C:\\Windows", "C:\\Program Files", "C:\\Program Files (x86)", // Windows
                "/bin", "/sbin", "/lib", "/usr", "/etc", // Unix/Linux
                "/Applications", "/System", "/Library", "/Volumes" // macOS
        };
        for (String systemDir : systemDirectories) {
            if (directory.getAbsolutePath().startsWith(systemDir)) {
                return true;
            }
        }
        return false;
    }
}
