package com.umarzaii.classreminder.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class SubjectModel {

    public String subjectID;
    public String subjectName;

    public SubjectModel() {

    }

    public SubjectModel(String subjectID, String subjectName) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("subjectID", subjectID);
        result.put("subjectName", subjectName);

        return result;
    }

}
