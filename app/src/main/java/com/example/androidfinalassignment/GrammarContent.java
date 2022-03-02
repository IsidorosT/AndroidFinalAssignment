package com.example.androidfinalassignment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrammarContent {
    public String Id;
    public String Title;
    public String Description;
    public HashMap<String,String> Info;
    GrammarContent(String id, Map<String,Object> values){
        Id = id;
        Title = values.get("Title").toString();
        Description = values.get("Description").toString();
        Info = (HashMap<String, String>) values.get("Info");
    }
}
