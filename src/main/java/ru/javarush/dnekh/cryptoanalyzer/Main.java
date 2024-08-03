package ru.javarush.dnekh.cryptoanalyzer;

import ru.javarush.dnekh.cryptoanalyzer.io.FileHandler;
import ru.javarush.dnekh.cryptoanalyzer.model.CaesarCipher;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        CaesarCipher cipher = new CaesarCipher();

        String text = "Привет, медвед!";
        int key = 5;

        String encryptedText = cipher.encrypt(text.toLowerCase(), key);
        System.out.println("Encrypted: " + encryptedText);

        String decryptedText = cipher.decrypt(encryptedText, key);
        System.out.println("Decrypted: " + decryptedText);

        System.out.println("-------------");

        String pathToFile = "src/test.txt";
        FileHandler fileHandler = new FileHandler();
        try {
            System.out.println(fileHandler.readFile(pathToFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            fileHandler.writeFile(pathToFile, "Hello Kitty Super Mors!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
