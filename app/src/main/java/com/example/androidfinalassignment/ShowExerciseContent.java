package com.example.androidfinalassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ShowExerciseContent extends AppCompatActivity {

    ExerciseContent Content;
    Button BackButton;
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
        List<Integer> Exercises = new LinkedList<>();
        for(int i = 0;i<4;i++){
            int index = rand.nextInt(Content.ExerciseData.size());
            while(Exercises.contains(index)){
                index = rand.nextInt(Content.ExerciseData.size());
            }

        }
    }
}