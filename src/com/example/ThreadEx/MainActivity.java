package com.example.ThreadEx;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity implements CharacterSource, CharacterListener {
    protected RandomCharacterGenerator mProducer = new RandomCharacterGenerator();
    private CharacterEventHandler mHandler = new CharacterEventHandler();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mProducer.start();
        layoutSetup();

    }



    //Adding buttons to the layout and launching the scanner app if button is clicked.
    public void layoutSetup() {
        ((Button)findViewById(R.id.enter)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputString = ((EditText)findViewById(R.id.input)).getText().toString();
                newCharacter(new CharacterEvent(MainActivity.this, inputString));
            }
        });
        ((Button)findViewById(R.id.edit_message)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                launchScanner();
            }
        });
        mProducer.addCharacterListener(this);
        this.addCharacterListener(this);
    }

    public boolean launchScanner() {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS);

        if(list.size()>0) {
            startActivityForResult(intent, 0);
            return true;
        } else {
            //prompt to install zxing
            Intent goToMarket = new Intent(Intent.ACTION_VIEW);
            goToMarket.setData(Uri.parse("market://details?id=com.google.zxing.client.android"));
            startActivity(goToMarket);
            return false;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            String tmp = intent.getStringExtra("SCAN_RESULT");
            TextView textView = (TextView)findViewById(R.id.output);
            String originalText = textView.getText().toString();

            textView.setText(originalText + tmp ); // sets the text from the scanner to output from the scanner
            ((EditText)findViewById(R.id.input)).setText(tmp); //Add the scanned text to the input field.

        }
    }

    public synchronized void processInput(final CharacterEvent ce) {
        final TextView textBox = (TextView)findViewById(R.id.output);
        final String originalText = textBox.getText().toString();
        runOnUiThread(new Runnable() {
            public void run() {
                textBox.setText(originalText + ce.c);
            }
        });
    }

    public void addCharacterListener(CharacterListener cl) {
        mHandler.addCharacterListener(cl);
    }

    public void removeCharacterListener(CharacterListener cl) {
        mHandler.removeCharacterListener(cl);
    }

    public void nextCharacter() {
        throw new IllegalStateException("input is produced by user");
    }

    public void newCharacter(CharacterEvent ce) {
        processInput(ce);
    }
}
