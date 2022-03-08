package com.example.androidfinalassignment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import org.apache.tika.language.LanguageIdentifier;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;



public class ShowExerciseContent extends AppCompatActivity {

    ExerciseContent Content;
    Button BackButton;
    Button SubmitButton;
    TextView LocationView;
    Object[] Exercisekeys;
    List<EditText> Answers = new LinkedList<>();
    SpeechRecognizer speechRecognizer;

    List<Boolean> ActiveSounds = new LinkedList<>();
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    LocationManager locationManager;
    public Context getContext(){
        return this.getApplicationContext();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exercise_content);
        //We taking Record Audio Permissions for Speech Recognition - SpeechToText

        LocationView = findViewById(R.id.locationView);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location loc = new location();
        /*
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }

         */

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, loc);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        BackButton = findViewById(R.id.BackExerciseButton);
        BackButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        Intent map = new Intent(ShowExerciseContent.this,TabbedHome.class);
                        startActivity(map);

                    }
                }
        );
        String cont = extras.getString("Content");

        Content = new Gson().fromJson(cont,ExerciseContent.class);

        LinearLayout layout = findViewById(R.id.exerciselayout);

        TextView title = new TextView(this.getApplicationContext());
        title.setText(Content.Title+"\n");
        layout.addView(title);

        TextView description = new TextView(this.getApplicationContext());
        description.setText(Content.Description+"\n");
        layout.addView(description);

        Random rand = new Random();
        Object[] keys = Content.ExerciseData.keySet().toArray();
        List<Integer> indexes = new LinkedList<>();
        HashMap<String,String> exercises = new HashMap<>();
        for(int i = 0;i<6;i++){
            int index = rand.nextInt(keys.length);
            while(indexes.contains(index)){
                index = rand.nextInt(keys.length);
            }
            exercises.put(String.valueOf(keys[index]),Content.ExerciseData.get(String.valueOf(keys[index])));
            indexes.add(index);
        }
        Exercisekeys = exercises.keySet().toArray();
        for(int i = 0;i<Exercisekeys.length;i++){
            LinearLayout lay = new LinearLayout(this.getApplicationContext());
            lay.setOrientation(LinearLayout.HORIZONTAL);
            TextView question = new TextView(this.getApplicationContext());
            question.setText(String.valueOf(Exercisekeys[i])+" = ");
            lay.addView(question);
            EditText answer = new EditText(this.getApplicationContext());
            lay.addView(answer);
            Answers.add(answer);
            LanguageIdentifier identifier = new LanguageIdentifier(question.getText().toString());
            if(identifier.getLanguage().equalsIgnoreCase("el")){
                SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

                final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                //Here we implement the listener for Speech Recognition - SpeechToText
                speechRecognizer.setRecognitionListener(new RecognitionListener() {
                    @Override
                    public void onReadyForSpeech(Bundle params) {

                    }

                    @Override
                    public void onBeginningOfSpeech() {

                    }

                    @Override
                    public void onRmsChanged(float rmsdB) {

                    }

                    @Override
                    public void onBufferReceived(byte[] buffer) {

                    }

                    @Override
                    public void onEndOfSpeech() {

                    }

                    @Override
                    public void onError(int error) {
                        System.out.println("onError"+error);
                    }

                    @Override
                    public void onResults(Bundle results) {
                        //Here we taking results from voice
                        ArrayList<String> data = results.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);

                        answer.setText(data.get(0));
                    }

                    @Override
                    public void onPartialResults(Bundle partialResults) {
                        ArrayList<String> data = partialResults.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);

                        answer.setText(data.get(0));
                    }

                    @Override
                    public void onEvent(int eventType, Bundle params) {

                    }
                });
                Button MicButton = new Button(this.getApplicationContext());
                MicButton.setText("TestSpeech");
                MicButton.setBackgroundColor(Color.LTGRAY);
                ActiveSounds.add(false);
                final int x = ActiveSounds.size()-1;
                //This is the Mic Button which enables and disables speechToText
                MicButton.setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                if(ActiveSounds.get(x)){
                                    //Stop Listening
                                    speechRecognizer.stopListening();
                                    MicButton.setBackgroundColor(Color.LTGRAY);
                                    ActiveSounds.set(x,false);
                                }else{
                                    //Start Listening
                                    speechRecognizer.startListening(speechRecognizerIntent);
                                    MicButton.setBackgroundColor(Color.RED);
                                    ActiveSounds.set(x,true);
                                }
                            }
                        }
                );
                lay.addView(MicButton);
            }
            layout.addView(lay);
        }
        SubmitButton = findViewById(R.id.SubmitButton);
        SubmitButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        int score = 0;
                        for(int i = 0;i<Exercisekeys.length;i++) {
                            if(exercises.get(String.valueOf(Exercisekeys[i])).equalsIgnoreCase(Answers.get(i).getText().toString())){
                                score++;
                            }
                        }
                        LinearLayout vert = new LinearLayout(getContext());
                        vert.setOrientation(LinearLayout.VERTICAL);
                        PopupWindow window = new PopupWindow(getContext());
                        TextView view = new TextView(getContext());
                        view.setTextColor(Color.WHITE);
                        view.setText("\n\n\n\n\n\n\n                         Your Score is "+score+"/"+Exercisekeys.length+"\n\n\n\n\n\n");
                        vert.addView(view);
                        Button Back = new Button(getContext());
                        Back.setText("Return");
                        Back.setOnClickListener(
                                new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Intent map = new Intent(ShowExerciseContent.this,TabbedHome.class);
                                        startActivity(map);

                                    }
                                }
                        );
                        vert.addView(Back);
                        window.setContentView(vert);
                        window.setHeight(1000);
                        window.setWidth(1000);
                        window.showAtLocation(findViewById(R.id.exerciselayout), Gravity.CENTER, 0, 0);
                        //Intent map = new Intent(ShowExerciseContent.this,TabbedHome.class);
                        //startActivity(map);

                    }
                }
        );



    }
    class location implements LocationListener {
        Boolean one = true;
        private final DecimalFormat df = new DecimalFormat("0.00");
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if(one){
                String latitude = df.format(location.getLatitude());
                String longitude = df.format(location.getLongitude());
                LocationView.setText("Location\nLatitude: "+ latitude + "\nLongitude: "+longitude);
                one = false;
            }

        }
        @Override
        public void onProviderDisabled(String arg0) {}
        @Override
        public void onProviderEnabled(String arg0) {}



        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}

    }
}