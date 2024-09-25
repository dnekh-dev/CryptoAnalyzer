package ru.javarush.dnekh.cryptoanalyzer.ui;

/**
 * Enum representing the available options in the command line interface menu.
 * Each enum constant corresponds to a specific action the user can perform in the application.
 */
public enum MenuOption {

    ENCRYPT("1. Encrypt text from file"),
    DECRYPT("2. Decrypt text from file with key"),
    BRUTE_FORCE("3. Brute Force Decrypt"),
    HELP("4. Help"),
    EXIT("5. Exit");

    private final String description;

    /**
     * Constructor for the enum constant, associates a description with each menu option.
     *
     * @param description the text description of the menu option
     */
    MenuOption(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the menu option.
     *
     * @return a string representing the description of the menu option
     */
    public String getDescription() {
        return description;
    }

    /**
     * Prints all available menu options to the console.
     * This method iterates through all enum constants and displays their descriptions.
     */
    public static void printMenu() {
        for (MenuOption option : MenuOption.values()) {
            System.out.println(option.getDescription());
        }
    }

    /**
     * Converts a string input into the corresponding MenuOption.
     * This method is used to map user input to a specific menu option.
     *
     * @param option the string representation of the menu option number
     * @return the MenuOption corresponding to the given input
     * @throws IllegalArgumentException if the input does not match any menu option
     */
    public static MenuOption fromString(String option) {
        // Use switch expression to map user input to the correct enum constant
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