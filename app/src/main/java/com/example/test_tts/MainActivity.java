package com.example.test_tts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextToSpeech t1;
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Intent TTSIntent = new Intent();
            TTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
            startActivityForResult(TTSIntent, 2);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "not available check tts data");
        }

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);

                    int available = t1.isLanguageAvailable(new Locale("es", "ES"));
                    Log.v(TAG, "is available result es: " + available);

                    int available2 = t1.isLanguageAvailable(new Locale("ab"));
                    Log.v(TAG, "is available result ab: " + available2);
                }
            }
        });
    }

    public void speak(View view) {
        String testSpeech = "test test test test test test test.";

        t1.speak(testSpeech, TextToSpeech.QUEUE_ADD, null, "test");
    }

    public void stop(View view) {
        int val = t1.stop();
    }

    public void speakSpanishExample(View view) {
        t1.setLanguage(new Locale("es", "ES"));

        String testSpeech = "Hola, Hola, Hola, Hola, Hola, Hola.";

        t1.speak(testSpeech, TextToSpeech.QUEUE_ADD, null, "test");
    }

    public void speakEnglishExample(View view) {
        t1.setLanguage(Locale.ENGLISH);

        String testSpeech = "Hello, Hello, Hello, Hello, Hello, Hello.";

        t1.speak(testSpeech, TextToSpeech.QUEUE_ADD, null, "test");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                Log.e(TAG, "data pass");
            } else {
                Log.e(TAG, "data not pass");
            }
        }
    }
}