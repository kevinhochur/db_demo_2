package kevinli.mobileapp.db_demo_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private DatabaseManager dbManager = new DatabaseManager(this);

    private String TAG = "MainActivity";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        try {
            dbManager.open();
            dbManager.fetch();
            ArrayList<HashMap<String, String>> trackList_main = trackInfo.trackList;
            SimpleAdapter adapter = new SimpleAdapter(
                    this,
                    trackList_main,
                    R.layout.list_view_layout,
                    new String[] { trackInfo.TITLE, trackInfo.DISTRICT, trackInfo.ROUTE },
                    new int[] { R.id.title, R.id.district, R.id.route }
            );
            System.out.println("SimpleAdapter Started!");
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            HashMap<String, String> contact = trackInfo.trackList.get(position);
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle(contact.get(trackInfo.TITLE));
                            builder.setMessage("How to Access: " + "\n" + contact.get(trackInfo.HOW_TO_ACCESS));
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
            );
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }
    }

    // Create the menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_download, menu);
        return true;
    }

    // Determines if Action bar item was selected. If true then do corresponding action.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Updating the Data", Toast.LENGTH_SHORT).show();
        JsonHandlerThread handler = new JsonHandlerThread();
        handler.start();
        try {
            handler.join();
            String title_en;
            String district_en;
            String route_en;
            String howToAccess_en;
            for (int i = 0; i < trackInfo.trackList.size(); i++) {
                title_en = String.valueOf(trackInfo.trackList.get(i).get("title"));
                district_en = String.valueOf(trackInfo.trackList.get(i).get("district"));
                route_en = String.valueOf(trackInfo.trackList.get(i).get("route"));
                howToAccess_en = String.valueOf(trackInfo.trackList.get(i).get("how_to_access"));
                dbManager.insert(title_en, district_en, route_en, howToAccess_en);
                System.out.println("Inserted all data!");
                //String resultFetched = String.valueOf(trackInfo.trackList);
                //System.out.println("Result Fetched: " + "\n" + resultFetched);
                Toast.makeText(this, "Data is Up to Date", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
