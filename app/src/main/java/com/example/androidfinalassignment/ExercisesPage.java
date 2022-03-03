package com.example.androidfinalassignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExercisesPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExercisesPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<ExerciseContent> ExerciseContents = new LinkedList<>();
    LinearLayout Cards;
    FirebaseFirestore db;
    public ExercisesPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExercisesPage.
     */
    // TODO: Rename and change types and number of parameters
    public static ExercisesPage newInstance(String param1, String param2) {
        ExercisesPage fragment = new ExercisesPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises_page, container, false);
        Cards = view.findViewById(R.id.linearexercise);

        db = FirebaseFirestore.getInstance();

        db.collection("GrammarContents").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ExerciseContent book = new ExerciseContent(document.getId(),document.getData());
                        ExerciseContents.add(book);
                    }
                    LoadViews();
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    public Context RetrieveContext(){
        return this.getContext();
    }
    public void LoadViews() {
        for (int i = 0; i < ExerciseContents.size(); i++) {
            CardView card = new CardView(this.getContext());
            LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutparams.setMargins(0,100,0,100);
            card.setLayoutParams(layoutparams);

            LinearLayout cardlayout = new LinearLayout(this.getContext());
            cardlayout.setOrientation(LinearLayout.VERTICAL);

            TextView text = new TextView(this.getContext());
            text.setText(ExerciseContents.get(i).Title);
            cardlayout.addView(text);
            TextView description = new TextView(this.getContext());
            description.setText(ExerciseContents.get(i).Description);
            cardlayout.addView(description);
            Button button = new Button(this.getContext());
            button.setText("View");
            ExerciseContent c = ExerciseContents.get(i);
            button.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            String jsonOrder = new Gson().toJson(c);
                            Bundle extras = new Bundle();
                            extras.putString("Content",jsonOrder);
                            Intent map = new Intent(RetrieveContext(),ShowExerciseContent.class);
                            map.putExtras(extras);
                            startActivity(map);

                        }
                    }
            );
            cardlayout.addView(button);
            card.addView(cardlayout);
            Cards.addView(card);
        }
    }
}