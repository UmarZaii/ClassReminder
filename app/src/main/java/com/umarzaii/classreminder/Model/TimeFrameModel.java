package com.umarzaii.classreminder.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class TimeFrameModel {

    public String dayID;
    public String timeGap;
    public String timeID;
    public String userClassID;
    public String classLocationID;
    public String subjectID;

    public static final String strUserClass = "userClass";
    public static final String strClassLocation = "classLocation";

    public TimeFrameModel() {

    }

    public TimeFrameModel(String timeGap, String timeID, String userClassID, String classLocationID, String subjectID) {
        this.timeGap = timeGap;
        this.timeID = timeID;
        this.userClassID = userClassID;
        this.classLocationID = classLocationID;
        this.subjectID = subjectID;
    }

    public String getUserClassID() {
        return userClassID;
    }

    public String getClassLocationID() {
        return classLocationID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public String getDayID() {
        return dayID;
    }

    public String getTimeGap() {
        return timeGap;
    }

    public String getTimeID() {
        return timeID;
    }

    @Exclude
    public Map<String, Object> classLocationToMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("timeGap", timeGap);
        result.put("timeID", timeID);
        result.put("userClassID", userClassID);
        result.put("subjectID", subjectID);

        return result;
    }

    @Exclude
    public Map<String, Object> userClassToMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("timeGap", timeGap);
        result.put("timeID", timeID);
        result.put("classLocationID", classLocationID);
        result.put("subjectID", subjectID);

        return result;
    }

    //TimeFrame Flaws
    @Exclude
    private Map<String, Object> classLocationInitDetails(String timeGap, String timeID, String userClassID, String subjectID) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("timeGap", timeGap);
        result.put("timeID", timeID);
        result.put("userClassID", userClassID);
        result.put("subjectID", subjectID);

        return result;
    }

    //TimeFrame Flaws
    @Exclude
    private Map<String, Object> userClassInitDetails(String timeGap, String timeID, String classLocationID, String subjectID) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("timeGap", timeGap);
        result.put("timeID", timeID);
        result.put("classLocationID", classLocationID);
        result.put("subjectID", subjectID);

        return result;
    }

    //TimeFrame Flaws
    @Exclude
    private Map<String, Object> timeFrameHourLoop(String initType, String dayID) {
        ArrayList<String> time = new ArrayList<String>();
        time.add("0800to0900");
        time.add("0900to1000");
        time.add("1000to1100");
        time.add("1100to1200");
        time.add("1200to1300");
        time.add("1300to1400");
        time.add("1400to1500");
        time.add("1500to1600");
        time.add("1600to1700");
        time.add("1700to1800");

        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> timeFrameHour = new HashMap<>();

        for (String timeID: time) {
            String timeGap = timeID.substring(0,2) + ".00 - " + timeID.substring(6,8) + ".00";
            Map<String, Object> details = null;
            if (initType.equals(strUserClass)) {
                details = userClassInitDetails(timeGap,timeID,"None","None");
            } else if (initType.equals(strClassLocation)) {
                details = classLocationInitDetails(timeGap,timeID,"None","None");
            }
            timeFrameHour.put(timeID, details);
        }

        result.put("timeFrameHour", timeFrameHour);
        result.put("dayID", dayID);

        return result;
    }

    //TimeFrame Flaws
    @Exclude
    public Map<String, Object> timeFrameInitLoop(String initType) {
        ArrayList<String> day = new ArrayList<String>();
        day.add("Monday");
        day.add("Tuesday");
        day.add("Wednesday");
        day.add("Thursday");
        day.add("Friday");
        day.add("Saturday");
        day.add("Sunday");

        HashMap<String, Object> result = new HashMap<>();
        for (String dayID: day) {
            Map<String, Object> details = timeFrameHourLoop(initType,dayID);
            result.put(dayID, details);
        }

        return result;
    }

}
