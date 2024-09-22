package ru.javarush.dnekh.cryptoanalyzer.ui;

/**
 * Enum representing the available options in the command line interface menu.
 */
public enum MenuOption {

    ENCRYPT("1. Encrypt text from file"),
    DECRYPT("2. Decrypt text from file with key"),
    BRUTE_FORCE("3. Brute Force Decrypt"),
    HELP("4. Help"),
    EXIT("5. Exit");

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
            case "3" -> BRUTE_FORCE;
            case "4" -> HELP;
            case "5" -> EXIT;
            default -> throw new IllegalArgumentException("\nINVALID MENU OPTION: " + option + "\nTRY AGAIN!\n");
        };
    }
}