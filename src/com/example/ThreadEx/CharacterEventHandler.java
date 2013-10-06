package com.example.ThreadEx;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: jarrod
 * Date: 10/5/13
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class CharacterEventHandler {
    private Vector listeners = new Vector();

    public void addCharacterListener(CharacterListener cl) {
        listeners.add(cl);
    }

    public void removeCharacterListener(CharacterListener cl) {
        listeners.remove(cl);
    }

    public void newCharacter(CharacterSource cs, String c) {
        CharacterEvent ce = new CharacterEvent(cs, c);
        CharacterListener[] clArray = (CharacterListener[])listeners.toArray(new CharacterListener[0]);
        for(int i=0; i< clArray.length; i++) {
            clArray[i].newCharacter(ce);
        }
    }
}
