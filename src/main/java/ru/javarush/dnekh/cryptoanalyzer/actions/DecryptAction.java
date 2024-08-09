package ru.javarush.dnekh.cryptoanalyzer.actions;

import ru.javarush.dnekh.cryptoanalyzer.exception.InvalidCharacterException;
import ru.javarush.dnekh.cryptoanalyzer.model.CaesarCipher;

/**
 * Action for decrypting text using Caesar cipher.
 */
public class DecryptAction implements Action {

    private final CaesarCipher caesarCipher;

    public DecryptAction() {
        this.caesarCipher = new CaesarCipher();
    }

    @Override
    public String execute(String content, int key) throws InvalidCharacterException {
        return caesarCipher.decrypt(content, key);
    }
}
