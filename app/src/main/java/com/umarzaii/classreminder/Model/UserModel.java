package com.umarzaii.classreminder.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class UserModel {

    public String userID;
    public String userEmail;
    public String userName;
    public String userFacultyID;
    public String userCourseCode;

    public UserModel() {

    }

    public UserModel(String userID, String userEmail, String userName, String userFacultyID, String userCourseCode) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userFacultyID = userFacultyID;
        this.userCourseCode = userCourseCode;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userID", userID);
        result.put("userEmail", userEmail);
        result.put("userName", userName);
        result.put("userFacultyID", userFacultyID);
        result.put("userCourseCode", userCourseCode);

        return result;
    }

}
