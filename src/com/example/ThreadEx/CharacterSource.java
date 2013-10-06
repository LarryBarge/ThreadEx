package com.example.ThreadEx;

/**
 * Created with IntelliJ IDEA.
 * User: jarrod
 * Date: 10/2/13
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CharacterSource {
    public void addCharacterListener(CharacterListener cl);
    public void removeCharacterListener(CharacterListener cl);
    public void nextCharacter();
}
