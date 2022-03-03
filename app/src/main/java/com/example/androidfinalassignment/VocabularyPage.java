package com.example.androidfinalassignment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VocabularyPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VocabularyPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FirebaseFirestore db;
    List<VocabularyContent> VocabularyContents = new LinkedList<>();
    LinearLayout Cards;
    List<Bitmap> Images = new LinkedList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VocabularyPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VocabularyPage.
     */
    // TODO: Rename and change types and number of parameters
    public static VocabularyPage newInstance(String param1, String param2) {
        VocabularyPage fragment = new VocabularyPage();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vocabulary_page, container, false);
        Cards = view.findViewById(R.id.linearv);
        Cards.setOrientation(LinearLayout.VERTICAL);

        db = FirebaseFirestore.getInstance();

        db.collection("VocabularyContents").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        VocabularyContent book = new VocabularyContent(document.getId(),document.getData());
                        VocabularyContents.add(book);
                    }
                    LoadResources();
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    public void LoadResources() {
        for(int i = 0;i<VocabularyContents.size();i++){
            final int x = i;
            //Books.get(i).BookImagePath
            try{
                StorageReference pathReference = FirebaseStorage.getInstance().getReference().child(VocabularyContents.get(i).ImagePath);
                pathReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        byte[]image = bytes;
                        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                        Images.add(bmp);
                        if(x == VocabularyContents.size()-1){
                            LoadViews();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        int x = 0;
                        String text = exception.getMessage();
                    }
                });
            }catch(Exception e){

            }

        }
    }
    public Context RetrieveContext(){
        return this.getContext();
    }
    public void LoadViews() {
        for(int i = 0;i<VocabularyContents.size();i++){
            final int x = i;
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
            text.setText(VocabularyContents.get(i).Title);
            cardlayout.addView(text);
            ImageView description = new ImageView(this.getContext());
            description.setImageBitmap(Images.get(i));
            cardlayout.addView(description);
            Button button = new Button(this.getContext());
            button.setText("View");
            VocabularyContent c = VocabularyContents.get(i);
            button.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            String jsonOrder = new Gson().toJson(c);
                            Bundle extras = new Bundle();
                            extras.putString("Content",jsonOrder);
                            String jsonOrder2 = new Gson().toJson(Images.get(x));
                            extras.putString("Image",jsonOrder2);
                            Intent map = new Intent(RetrieveContext(),ShowVocabularyContent.class);
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