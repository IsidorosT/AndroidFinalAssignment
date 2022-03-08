package com.example.androidfinalassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.androidfinalassignment.ShowExerciseContent.MY_PERMISSIONS_REQUEST_LOCATION;

public class MainActivity extends AppCompatActivity {

    public TextView UsernameText;
    public TextView PasswordText;
    public Button LoginButton;

    private FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsernameText = findViewById(R.id.UsernameText);
        PasswordText = findViewById(R.id.PasswordText);
        LoginButton = findViewById(R.id.LoginButton);
        auth = FirebaseAuth.getInstance();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);


        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},101);
        }
        if (checkCallingOrSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 101);
            }
        }
        LoginButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        String username = "";
                        String password = "";
                        if(UsernameText.getText() != null)
                            username = UsernameText.getText().toString();
//                        if(PasswordText.getText() != null)
//                            password = PasswordText.getText().toString();
//                        if(!username.equals("") || !password.equals(""))
//                        {
//                            auth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task)
//                                {
//                                    if (task.isSuccessful())
//                                    {
//                                        user = auth.getCurrentUser();
                                          Intent map = new Intent(MainActivity.this,TabbedHome.class);
                                          startActivity(map);
//                                    }
//                                    else {
//                                        user = null;
//                                        Intent map = new Intent(MainActivity.this,MainActivity.class);
//                                        startActivity(map);
//                                    }
//                                }
//                            });
//                        }
                    }
                }
        );
    }
}