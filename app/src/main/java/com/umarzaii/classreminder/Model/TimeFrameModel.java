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
    public String classLocation;
    public String subjectID;

    public static final String strUserClass = "userClass";
    public static final String strClassLocation = "classLocation";

    public TimeFrameModel() {

    }

    public TimeFrameModel(String timeGap, String timeID, String userClassID) {
        this.timeGap = timeGap;
        this.timeID = timeID;
        this.userClassID = userClassID;
    }

    public TimeFrameModel(String timeGap, String timeID, String classLocation, String subjectID) {
        this.timeGap = timeGap;
        this.timeID = timeID;
        this.classLocation = classLocation;
        this.subjectID = subjectID;
    }

    public String getDayID() {
        return dayID;
    }

    @Exclude
    public Map<String, Object> classLocationToMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("timeGap", timeGap);
        result.put("timeID", timeID);
        result.put("userClass", userClassID);

        return result;
    }

    @Exclude
    public Map<String, Object> userClassToMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("timeGap", timeGap);
        result.put("timeID", timeID);
        result.put("classLocation", classLocation);
        result.put("subjectID", subjectID);

        return result;
    }

    //TimeFrame Flaws
    @Exclude
    private Map<String, Object> classLocationInitDetails(String timeGap, String timeID, String userClassID) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("timeGap", timeGap);
        result.put("timeID", timeID);
        result.put("userClassID", userClassID);

        return result;
    }

    //TimeFrame Flaws
    @Exclude
    private Map<String, Object> userClassInitDetails(String timeGap, String timeID, String classLocation, String subjectID) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("timeGap", timeGap);
        result.put("timeID", timeID);
        result.put("classLocation", classLocation);
        result.put("subjectID", subjectID);

        return result;
    }

    //TimeFrame Flaws
    @Exclude
    private Map<String, Object> classLocationTimeInitLoop(String dayID) {
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
        for (String timeID: time) {
            String timeGap = timeID.substring(0,2) + ".00 - " + timeID.substring(6,8) + ".00";
            Map<String, Object> details = classLocationInitDetails(timeGap,timeID,"None");
            result.put(timeID, details);
        }
        result.put("dayID", dayID);

        return result;
    }

    //TimeFrame Flaws
    @Exclude
    private Map<String, Object> userClassTimeInitLoop(String dayID) {
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
        for (String timeID: time) {
            String timeGap = timeID.substring(0,2) + ".00 - " + timeID.substring(6,8) + ".00";
            Map<String, Object> details = userClassInitDetails(timeGap,timeID,"None","None");
            result.put(timeID, details);
        }
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

        if (initType.equals(strUserClass)) {
            for (String dayID: day) {
                Map<String, Object> details = userClassTimeInitLoop(dayID);
                result.put(dayID, details);
            }
        } else if (initType.equals(strClassLocation)) {
            for (String dayID: day) {
                Map<String, Object> details = classLocationTimeInitLoop(dayID);
                result.put(dayID, details);
            }
        }

        return result;
    }

}
