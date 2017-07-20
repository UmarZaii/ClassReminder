package com.umarzaii.classreminder.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DatabaseHandler {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private static boolean isPersistenceEnabled = false;

    private String userID;

    private String tblUser = "tblUser";
    private String tblUniversity = "tblUniversity";
    private String tblCourse = "tblCourse";
    private String userClassList = "userClassList";

    public static final String tblClassLocation = "tblClassLocation";
    public static final String tblUserClass = "tblUserClass";
    public static final String tblSubject = "tblSubject";

    public static final String courseID = "courseID";
    public static final String courseAdmin = "courseAdmin";
    public static final String courseLecturer = "courseLecturer";
    public static final String uniAdminDepartment = "uniAdminDepartment";
    public static final String uniHeadDepartment = "uniHeadDepartment";
    public static final String uniLecturer = "uniLecturer";
    public static final String uniStudent = "uniStudent";
    public static final String credentials = "credentials";

    public static final String timeFrameDay = "timeFrameDay";
    public static final String timeFrameHour = "timeFrameHour";

    public static final String PSMZAID = "a2wx2pyFWJbRcOYWqkwwu7YwgRo2";

    public DatabaseHandler() {
        if (!isPersistenceEnabled) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            isPersistenceEnabled = true;
        }
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public StorageReference getTblBusinessStorage(String businessID, String pathFile) {
        return storageReference.child(businessID).child(pathFile);
    }

    public DatabaseReference getTblUser() {
        return databaseReference.child(tblUser);
    }

    public DatabaseReference getTblUser(String userID) {
        return databaseReference.child(tblUser).child(userID);
    }

    public DatabaseReference getTblUserCredentials(String userID) {
        return databaseReference.child(tblUser).child(userID).child(credentials);
    }

    public DatabaseReference getTblUserCredentialsCourseID(String userID) {
        return databaseReference.child(tblUser).child(userID).child(credentials).child(courseID);
    }

    public DatabaseReference getTblUniversity(String uniID) {
        return databaseReference.child(tblUniversity).child(uniID);
    }

    public DatabaseReference getTblUniversityCourse(String uniID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblCourse);
    }

    public DatabaseReference getTblUniversityCourse(String uniID, String courseID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblCourse).child(courseID);
    }

    public DatabaseReference getTblUniversityCourseAdmin(String uniID, String courseID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblCourse).child(courseID).child(courseAdmin);
    }

    public DatabaseReference getTblUniversityCourseLecturer(String uniID, String courseID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblCourse).child(courseID).child(courseLecturer);
    }

    public DatabaseReference getTblUniversityCourseClassList(String uniID, String courseID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblCourse).child(courseID).child(userClassList);
    }

    public DatabaseReference getTblUniversityClassLocation(String uniID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblClassLocation);
    }

    public DatabaseReference getTblUniversityClassLocation(String uniID, String classLocationID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblClassLocation).child(classLocationID);
    }

    public DatabaseReference getTblUniversityClassLocationTimeFrameDay(String uniID, String classLocationID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblClassLocation).child(classLocationID)
                .child(timeFrameDay);
    }

    public DatabaseReference getTblUniversityClassLocationTimeFrameHour(String uniID, String classLocationID, String dayID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblClassLocation).child(classLocationID)
                .child(timeFrameDay).child(dayID).child(timeFrameHour);
    }

    public DatabaseReference getTblUniversityClassLocationTimeFrameHour(String uniID, String classLocationID, String dayID, String timeFrameID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblClassLocation).child(classLocationID)
                .child(timeFrameDay).child(dayID).child(timeFrameHour).child(timeFrameID);
    }

    public DatabaseReference getTblUniversityUserClass(String uniID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblUserClass);
    }

    public DatabaseReference getTblUniversityUserClass(String uniID, String userClassID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblUserClass).child(userClassID);
    }

    public DatabaseReference getTblUniversityUserClassTimeFrameDay(String uniID, String userClassID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblUserClass).child(userClassID)
                .child(timeFrameDay);
    }

    public DatabaseReference getTblUniversityUserClassTimeFrameHour(String uniID, String userClassID, String dayID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblUserClass).child(userClassID)
                .child(timeFrameDay).child(dayID).child(timeFrameHour);
    }

    public DatabaseReference getTblUniversityUserClassTimeFrameHour(String uniID, String userClassID, String dayID, String timeFrameID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblUserClass).child(userClassID)
                .child(timeFrameDay).child(dayID).child(timeFrameHour).child(timeFrameID);
    }

    public DatabaseReference getTblUniversitySubject(String uniID) {
        return databaseReference.child(tblUniversity).child(uniID).child(tblSubject);
    }

    public String getUserID() {
        if (firebaseAuth.getCurrentUser() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }
        return userID;
    }

}
