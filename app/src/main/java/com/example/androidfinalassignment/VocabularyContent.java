package com.example.androidfinalassignment;

import java.util.HashMap;
import java.util.Map;

public class VocabularyContent {
    public String Id;
    public String Title;
    public String ImagePath;
    public HashMap<String,String> Vocabulary;
    VocabularyContent(String id, Map<String,Object> values){
        Id = id;
        Title = values.get("Title").toString();
        ImagePath = values.get("ImagePath").toString();
        Vocabulary = (HashMap<String, String>) values.get("Vocabulary");
    }
}
