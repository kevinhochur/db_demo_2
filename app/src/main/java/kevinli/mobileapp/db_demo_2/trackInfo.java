package kevinli.mobileapp.db_demo_2;

import java.util.ArrayList;
import java.util.HashMap;

public class trackInfo {
    public static String TITLE = "title";
    public static String DISTRICT = "district";
    public static String ROUTE = "route";
    public static String HOW_TO_ACCESS = "how_to_access";

    // An array list that stores many HashMaps
    public static ArrayList<HashMap<String, String>> trackList = new ArrayList<>();

    // Create and add contact to contact list
    public static void addTrack(String Title_en, String District_en, String Route_en, String HowToAccess_en) {
        // Create a track
        HashMap<String, String> track = new HashMap<>();
        track.put(TITLE, Title_en);
        track.put(DISTRICT, District_en);
        track.put(ROUTE, Route_en);
        track.put(HOW_TO_ACCESS, HowToAccess_en);

        // Add the track to the track list
        trackList.add(track);
    }
}
