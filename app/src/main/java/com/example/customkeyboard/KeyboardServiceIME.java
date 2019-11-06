package com.example.customkeyboard;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Toast;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class KeyboardServiceIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private Keyboard mKeyBoard;
    private KeyboardView mKeyView;
    private static  final String FILE_NAME="plik.txt";


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(takePictureIntent);

    }
    public void openUTP (String url){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void save () {
        String text="CUSTOM KEYBOARD";
        FileOutputStream fos=null;

        try {
            fos=openFileOutput(FILE_NAME, MODE_PRIVATE);
            try {
                fos.write(text.getBytes());
                Toast.makeText(this,"SAVED to "+getFilesDir()+"/"+FILE_NAME,Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public View onCreateInputView() {
        mKeyView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        mKeyBoard = new Keyboard(this, R.xml.qwerty);
        mKeyView.setKeyboard(mKeyBoard);
        mKeyView.setOnKeyboardActionListener(this);
        return mKeyView;
    }
    public View SecondInputView() {
        mKeyView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        mKeyBoard = new Keyboard(this, R.xml.qwerty1);
        mKeyView.setKeyboard(mKeyBoard);
        mKeyView.setOnKeyboardActionListener(this);
        return mKeyView;
    }


    // Listeners
    @Override
    public void onPress(int primaryCode) {
        // no-op
    }

    @Override
    public void onRelease(int primaryCode) {
        // no-op
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        //1
        if (primaryCode == 49) {
            String mess = "I'm custom keyboard";
            InputConnection inputConnection = getCurrentInputConnection();
            inputConnection.commitText(String.valueOf(mess), 1);
        }
        //2
        if (primaryCode == 50) {
            final MediaPlayer soundMP = MediaPlayer.create(this, R.raw.sound);
            soundMP.start();
        }
        //3
        if (primaryCode == 51) {
            Toast.makeText(this, String.format("I'm Custom Keyboard"), Toast.LENGTH_SHORT).show();
        }
        //4
        if (primaryCode == 52) {
                save();
        }

        //5
        if (primaryCode == 53) {
            dispatchTakePictureIntent();
        }
        //6
        if (primaryCode == 54) {
            View v = SecondInputView();
            setInputView(v);
        }
        //7
        if (primaryCode == 55) {
            NfcAdapter nfcAdapter= NfcAdapter.getDefaultAdapter(this);

            if (nfcAdapter == null) {
                Toast.makeText(this, String.format("NFC BRAK"), Toast.LENGTH_SHORT).show();
            } else if (!nfcAdapter.isEnabled()) {
                Toast.makeText(this, String.format("NFC IS NOT ENABLED"), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, String.format("NFC IS ENABLED"), Toast.LENGTH_SHORT).show();
            }
        }
        //8
        if (primaryCode == 56) {
            Toast.makeText(getApplicationContext(), "Please activate NFC and press Back to return to the application!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }
        //9
        if (primaryCode == 57) {
            openUTP("https://www.utp.edu.pl/pl/");

        }
        //10
        if (primaryCode == 58) {
           Intent openMap=getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
            openMap.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(openMap);
        }
        //back
        if (primaryCode == 67) {
            View v = onCreateInputView();
            setInputView(v);
        }
    }



    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {
        // no-op
    }

    @Override
    public void swipeRight() {
        // no-op
    }

    @Override
    public void swipeDown() {
        // no-op
    }

    @Override
    public void swipeUp() {
        // no-op
    }
}