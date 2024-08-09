package ru.javarush.dnekh.cryptoanalyzer.actions;

import ru.javarush.dnekh.cryptoanalyzer.exception.InvalidCharacterException;

/**
 * Interface representing an action that can be performed on a text.
 */
public interface Action {

    String execute(String content, int key) throws InvalidCharacterException;

}
