package com.umarzaii.classreminder.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class UserClassModel {

    public String userClassID;

    public UserClassModel() {

    }

    public UserClassModel(String userClassID) {
        this.userClassID = userClassID;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userClassID", userClassID);

        return result;
    }

}
