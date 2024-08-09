package ru.javarush.dnekh.cryptoanalyzer.actions;

import ru.javarush.dnekh.cryptoanalyzer.exception.InvalidCharacterException;
import ru.javarush.dnekh.cryptoanalyzer.model.CaesarCipher;

/**
 * Action for encrypting text using Caesar cipher.
 */
public class EncryptAction implements Action {

    private final CaesarCipher caesarCipher;

    public EncryptAction() {
        this.caesarCipher = new CaesarCipher();
    }

    @Override
    public String execute(String content, int key) throws InvalidCharacterException {
        return caesarCipher.encrypt(content, key);
    }
}
