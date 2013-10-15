package com.example.ThreadEx;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity implements CharacterSource, CharacterListener {
    protected RandomCharacterGenerator mProducer = new RandomCharacterGenerator();
    private CharacterEventHandler mHandler = new CharacterEventHandler();
    private final String savedOutput = "com.example.ThreadEx.MainActivity.output";
    //changes for git push you ass.

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mProducer.start();
        layoutSetup(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(savedOutput, ((TextView)findViewById(R.id.output)).getText().toString());
    }

    //Adding buttons to the layout and launching the scanner app if button is clicked.
    public void layoutSetup(Bundle savedInstanceState) {
        ((Button)findViewById(R.id.enter)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputString = ((EditText)findViewById(R.id.input)).getText().toString();
                newCharacter(new CharacterEvent(MainActivity.this, inputString));
            }
        });
        ((Button)findViewById(R.id.launch_scanner)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                launchScanner();
            }
        });
        if(savedInstanceState!=null && savedInstanceState.containsKey(savedOutput)) {
            String restoredText = savedInstanceState.getString(savedOutput);
            if(restoredText!=null)
                ((TextView)findViewById(R.id.output)).setText(restoredText);

        }

        setListView();
        mProducer.addCharacterListener(this);
        this.addCharacterListener(this);
    }

    public void setListView(){
        Cursor dbCursor = DBSingleton.getInstance().getDatabase(this.getApplicationContext()).query(DBSchema.ScanSchema.TABLE_NAME,null,null,null,null,null,null);
        SimpleCursorAdapter dbCursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                dbCursor,
                new String[]{DBSchema.ScanSchema.COLUMN_VALUE, DBSchema.ScanSchema.COLUMN_DATE},
                new int[]{android.R.id.text1, android.R.id.text2} );
        ListView listView = (ListView)findViewById(R.id.scan_list);
        listView.setAdapter(dbCursorAdapter);
    }

    /*Launches Scanner App if installed, if not goes to install*/
    public boolean launchScanner() {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS);

        if(list.size()>0) {     //if the list from PackageManager returns >0 launch the scanner
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            String tmp = intent.getStringExtra("SCAN_RESULT");
            TextView textView = (TextView)findViewById(R.id.output);
            String originalText = textView.getText().toString();

            textView.setText(originalText + tmp ); // sets the text from the scanner to output from the scanner
            ((EditText)findViewById(R.id.input)).setText(tmp); //Add the scanned text to the input field.

            //adding the data to database
            addDataSQLite(tmp);
            setListView();
        }

    }


    /*Real meat of adding to database*/
    private void addDataSQLite(final String scanResult){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ContentValues contentValues = new ContentValues(); //Creating a contentValue
                contentValues.put(DBSchema.ScanSchema.COLUMN_ID,0);
                contentValues.put(DBSchema.ScanSchema.COLUMN_VALUE,scanResult); //Places the scanned result passed to addDataSQLite() into contentValue local variable created
                contentValues.put(DBSchema.ScanSchema.COLUMN_DATE, System.currentTimeMillis()/1000);
                DBSingleton.getInstance().getDatabase(MainActivity.this).insert(DBSchema.ScanSchema.TABLE_NAME,null,contentValues); //Creates a singleton instance of the database and inserts the value

            }
        });
        thread.start(); //Start Thread
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
