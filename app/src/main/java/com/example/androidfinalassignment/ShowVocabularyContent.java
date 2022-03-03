package com.example.androidfinalassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.Set;

public class ShowVocabularyContent extends AppCompatActivity {

    VocabularyContent Content;
    Button BackButton;
    Bitmap Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vocabulary_content);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        BackButton = findViewById(R.id.BackVocabularyButton);
        BackButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        Intent map = new Intent(ShowVocabularyContent.this,TabbedHome.class);
                        startActivity(map);

                    }
                }
        );
        String cont = extras.getString("Content");
        String image = extras.getString("Image");

        Content = new Gson().fromJson(cont,VocabularyContent.class);
        Image = new Gson().fromJson(image,Bitmap.class);

        LinearLayout layout = findViewById(R.id.vocabularylayout);

        TextView title = new TextView(this.getApplicationContext());
        title.setText(Content.Title+"\n\n");
        layout.addView(title);

        ImageView description = new ImageView(this.getApplicationContext());
        description.setImageBitmap(Image);
        layout.addView(description);

        TextView Vocabulary = new TextView(this.getApplicationContext());
        String text = "";
        Object[] keys = Content.Vocabulary.keySet().toArray();
        for(int i = 0;i<keys.length;i++){
            text += keys[i] + " = " +Content.Vocabulary.get(keys[i]) + "\n";
        }
        Vocabulary.setText(text);
        layout.addView(Vocabulary);
    }
}