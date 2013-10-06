package com.example.ThreadEx;

/**
 * Created with IntelliJ IDEA.
 * User: jarrod
 * Date: 10/2/13
 * Time: 5:22 PM
 * To change this template use File | Settings | File Templates.
 */
public final class CharacterEvent {
    public final CharacterSource cs;
    public final String c;

    public CharacterEvent(CharacterSource cs, String c) {
        this.cs = cs;
        this.c = c;
    }
}
