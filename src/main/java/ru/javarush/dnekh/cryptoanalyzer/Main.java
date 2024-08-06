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

        FileHandler fileHandler = new FileHandler();
        String pathToFile = "src/test.txt";
        String content = "Hello, world!";

        try {
            fileHandler.writeFile(pathToFile, content);

            String readContent = fileHandler.readFile(pathToFile);
            System.out.println("File content: " + readContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
