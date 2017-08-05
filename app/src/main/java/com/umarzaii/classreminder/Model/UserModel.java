package com.umarzaii.classreminder.Model;

import android.util.Log;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniAdminDepartment;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniHeadDepartment;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniLecturer;
import static com.umarzaii.classreminder.Handler.DatabaseHandler.uniStudent;

@IgnoreExtraProperties
public class UserModel {

    public String userID;
    public String userEmail;
    public String userName;

    public String uniID;
    public String userType;
    public String studentID;
    public String employeeID;
    public String courseID;
    public String userClassID;

    public UserModel() {

    }

    public UserModel(String userID, String userEmail, String userName) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    public UserModel(String userID, String uniID, String userType, String employeeID, String courseID) {
        this.userID = userID;
        this.uniID = uniID;
        this.userType = userType;
        this.employeeID = employeeID;
        this.courseID = courseID;
    }

    public UserModel(String userID, String uniID, String userType, String studentID, String courseID, String userClassID) {
        this.userID = userID;
        this.uniID = uniID;
        this.userType = userType;
        this.studentID = studentID;
        this.courseID = courseID;
        this.userClassID = userClassID;
    }

    @Exclude
    public Map<String, Object> userIDToMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userID", userID);

        return result;
    }

    @Exclude
    public Map<String, Object> detailsToMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userID", userID);
        result.put("userEmail", userEmail);
        result.put("userName", userName);

        return result;
    }

    @Exclude
    public Map<String, Object> credentialsEmployeeToMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uniID", uniID);
        result.put("userRole", userRoleToMap());
        result.put("employeeID", employeeID);
        result.put("courseID", courseID);

        return result;
    }

    @Exclude
    public Map<String, Object> credentialsStudentToMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uniID", uniID);
        result.put("userRole", userRoleToMap());
        result.put("studentID", studentID);
        result.put("courseID", courseID);
        result.put("userClassID", userClassID);

        return result;
    }

    @Exclude
    private Map<String, Object> userRoleToMap() {
        HashMap<String, Object> userRoleMap = new HashMap<>();
        HashMap<String, Object> result = new HashMap<>();

        if (userType.equals(uniAdminDepartment)) {

            userRoleMap.put("userType", uniAdminDepartment);
            result.put(uniAdminDepartment, userRoleMap);
            userRoleMap.put("userType", uniLecturer);
            result.put(uniLecturer, userRoleMap);

        } else if (userType.equals(uniHeadDepartment)) {

            userRoleMap.put("userType", uniHeadDepartment);
            result.put(uniHeadDepartment, userRoleMap);
            userRoleMap.put("userType", uniLecturer);
            result.put(uniLecturer, userRoleMap);

        } else if (userType.equals(uniLecturer)) {

            userRoleMap.put("userType", uniLecturer);
            result.put(uniLecturer, userRoleMap);

        } else {

            userRoleMap.put("userType", uniStudent);
            result.put(uniStudent, userRoleMap);

        }

        return result;
    }

}
