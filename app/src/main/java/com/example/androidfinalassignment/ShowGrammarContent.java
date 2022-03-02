package com.example.androidfinalassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

public class ShowGrammarContent extends AppCompatActivity {

    GrammarContent Content;
    Button BackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_grammar_content);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        BackButton = findViewById(R.id.BackGrammarButton);
        BackButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        Intent map = new Intent(ShowGrammarContent.this,TabbedHome.class);
                        startActivity(map);

                    }
                }
        );
        String cont = extras.getString("Content");

        Content = new Gson().fromJson(cont,GrammarContent.class);

        LinearLayout layout = findViewById(R.id.grammarlayout);

        TextView title = new TextView(this.getApplicationContext());
        title.setText(Content.Title+"\n\n");
        layout.addView(title);

        TextView description = new TextView(this.getApplicationContext());
        description.setText(Content.Description+"\n\n");
        layout.addView(description);

        TextView info = new TextView(this.getApplicationContext());
        String infotext = "";
        for(int i = 1;i<=Content.Info.size();i++){
            infotext += Content.Info.get(String.valueOf(i)) + "\n";
        }
        info.setText(infotext);
        layout.addView(info);

    }
}