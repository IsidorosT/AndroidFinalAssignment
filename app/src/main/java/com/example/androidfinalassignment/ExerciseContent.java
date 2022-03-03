package com.example.androidfinalassignment;

import java.util.HashMap;
import java.util.Map;

public class ExerciseContent {
    public String Id;
    public String Title;
    public String Description;
    public HashMap<String,String> ExerciseData;
    ExerciseContent(String id, Map<String,Object> values){
        Id = id;
        Title = values.get("Title").toString();
        Description = values.get("Description").toString();
        ExerciseData = (HashMap<String, String>) values.get("ExerciseData");
    }
}
