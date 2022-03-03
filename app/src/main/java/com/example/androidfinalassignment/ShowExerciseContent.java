package com.example.androidfinalassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ShowExerciseContent extends AppCompatActivity {

    ExerciseContent Content;
    Button BackButton;
    Button SubmitButton;

    Object[] Exercisekeys;
    List<EditText> Answers = new LinkedList<>();

    public Context getContext(){
        return this.getApplicationContext();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exercise_content);

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
}