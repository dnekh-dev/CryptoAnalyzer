package ru.javarush.dnekh.cryptoanalyzer.ui;

/**
 * Enum representing the available options in the command line interface menu.
 */
public enum MenuOption {

    ENCRYPT("1. Encrypt text from file"),
    DECRYPT("2. Decrypt text from file with key"),
    ENCRYPT_CONSOLE("3. Encrypt text from console"),
    DECRYPT_CONSOLE("4. Decrypt text from console with key"),
    HELP("5. Help"),
    EXIT("6. Exit");

    private final String description;

    MenuOption(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static void printMenu() {
        for (MenuOption option : MenuOption.values()) {
            System.out.println(option.getDescription());
        }
    }

    public static MenuOption fromString(String option) {
        return switch (option) {
            case "1" -> ENCRYPT;
            case "2" -> DECRYPT;
            case "3" -> ENCRYPT_CONSOLE;
            case "4" -> DECRYPT_CONSOLE;
            case "5" -> HELP;
            case "6" -> EXIT;
            default -> throw new IllegalArgumentException("Invalid menu option: " + option);
        };
    }
}
