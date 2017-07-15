package com.umarzaii.classreminder.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ClassLocationModel {

    public String classLocationID;

    public ClassLocationModel() {

    }

    public ClassLocationModel(String classLocationID) {
        this.classLocationID = classLocationID;
    }

    public String getClassLocationID() {
        return classLocationID;
    }

    @Exclude
    public Map<String, Object> detailsToMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("classLocationID", classLocationID);

        return result;
    }

}
