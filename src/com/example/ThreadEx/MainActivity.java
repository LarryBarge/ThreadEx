package com.example.ThreadEx;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onResume() {
        super.onResume();
        EditText textBox = (EditText)findViewById(R.id.characters);
        textBox.setText("Aaaaand... we're back");
    }

    public synchronized void showCharacter(CharacterEvent ce) {
        EditText textBox = (EditText)findViewById(R.id.characters);
        //String textBox.getText()
    }
}
