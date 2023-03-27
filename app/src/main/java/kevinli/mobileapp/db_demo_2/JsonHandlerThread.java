package kevinli.mobileapp.db_demo_2;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class JsonHandlerThread extends Thread {
    private static final String TAG = "JsonHandlerThread";
    // URL to get contacts JSON file
    private static String jsonUrl = "https://www.lcsd.gov.hk/datagovhk/facility/facility-fw.json";

    public static String makeRequest() {
        String response = null;
        try {
            URL url = new URL(jsonUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // Read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = inputStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    public static String inputStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            }
        }

        return sb.toString();
    }

    public void run() {
        String trackStr = makeRequest();
        Log.e(TAG, "Response from url: " + trackStr);
        if (trackStr != null) {
            try {
                // Getting JSON Array
                JSONArray tracks = new JSONArray(trackStr);
                // looping through All tracks
                for (int i = 0; i < tracks.length(); i++) {
                    JSONObject c = tracks.getJSONObject(i);

                    String id;
                    String title = c.getString("Title_en");
                    String district = c.getString("District_en");
                    String route = c.getString("Route_en");
                    String how_to_access = c.getString("HowToAccess_en");

                    // Add contact (name, email, address) to contact list
                    trackInfo.addTrack(title, district, route, how_to_access);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }
}

