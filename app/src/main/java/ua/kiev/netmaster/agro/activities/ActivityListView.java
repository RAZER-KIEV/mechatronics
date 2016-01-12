package ua.kiev.netmaster.agro.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.kiev.netmaster.agro.R;
import ua.kiev.netmaster.agro.domain.MyDownTask;
import ua.kiev.netmaster.agro.domain.MySensorAdapter;
import ua.kiev.netmaster.agro.domain.MyZoneAdapter;
import ua.kiev.netmaster.agro.domain.Sensor;
import ua.kiev.netmaster.agro.domain.Zone;


public class ActivityListView extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final String types[] = {"SOLAR","HUMIDITY","TEMPERATURE","WINDSPEED","CHARGE","PRESSURE","HUMIDITY_15", "HUMIDITY_65"};
    private String result, zone_id, sensorId;
    private Gson gson;
    //private Zone zone;
    private static List<Zone> zoneList;
    private static List<Sensor> sensorList;
    private List<Sensor> temp;
    private MyZoneAdapter myZoneAdapter;
    private MySensorAdapter mySensorAdapter;
    //private View header;
    private boolean zoneListHasHeader, sensorListHasHeader, isSensorListVisible;
    private View progressBar;
    private ListView sensorListView;
    private ListView zoneListView;
    private TableRow buttonsRow;
    private Spinner spinner;
    private static Sensor curSensor;
    TypeToken<List<Zone>> tokenZone;
    TypeToken<List<Sensor>> tokenSensor;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LoginActivity.LOG, "ActivityListView. onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list_view);
        gson = new Gson();

        progressBar = findViewById(R.id.progressBarList);
        sensorListView = (ListView) findViewById(R.id.sensorListView1);
        zoneListView = (ListView) findViewById(R.id.listViewZones);
        buttonsRow = (TableRow)findViewById(R.id.buttons);


        CookieHandler.getDefault();

        prepareSpinner();

        tokenZone = new TypeToken<List<Zone>>(){};
        tokenSensor = new TypeToken<List<Sensor>>() {};

        showSensorsMode(false);

        onClickRefresh(null);

        //ListView listView = (ListView) findViewById(R.id.listViewOrders);
        //onClick(null);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void prepareSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.snipper_item, types);
        adapter.setDropDownViewResource(R.layout.snipper_item);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Select type os sensors");
        //spinner.setDrawingCacheBackgroundColor(Color.RED);//// TODO: 12.01.2016
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String filtername = types[position].toLowerCase();
                temp = new ArrayList<>();
                for(Sensor sensor: sensorList){
                    if(sensor.getType().equals(filtername)){
                        temp.add(sensor);
                    }
                }
                Log.d(LoginActivity.LOG, " temp.size() = " + temp.size());
                showSensorList(temp);
                        // показываем позиция нажатого элемента
                //Toast.makeText(getBaseContext(), "Filter = " + filtername, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        Log.d(LoginActivity.LOG, "ActivityListView. onResume(): isSensorListVisible ="+isSensorListVisible);
        if(isSensorListVisible){

        }else {
            onClickRefresh(null);
        }


    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


   /* private View createHeader(String first, String second) {
        View v = getLayoutInflater().inflate(R.layout.item, null);
        ((TextView) v.findViewById(R.id.zoneId)).setText(first);
        (v.findViewById(R.id.zoneId)).setBackgroundColor(Color.parseColor("#FF9800"));
        ((TextView) v.findViewById(R.id.zoneName)).setText(second);
        (v.findViewById(R.id.zoneName)).setBackgroundColor(Color.parseColor("#FF9800"));
        v.setClickable(false);

        return v;
        }*/


    public void onClickRefresh(View view) {
        Log.d(LoginActivity.LOG, "ActivityListView. onClickRefresh");
        try {
            result = new MyDownTask("zones/get", this).execute().get();
            zoneList = gson.fromJson(result, tokenZone.getType());
            Collections.sort(zoneList);
            myZoneAdapter = new MyZoneAdapter(this, zoneList);
           /* if (!zoneListHasHeader) {
                zoneListView.addHeaderView(createHeader("ID", "FIELD NAME"),null,false);
                //zoneListView.addHeaderView(createHeader().);
                zoneListHasHeader = true;
            }*/
            zoneListView.setAdapter(myZoneAdapter);
            zoneListView.setOnItemClickListener(this);

            showSensorsMode(false);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "No data... :(",Toast.LENGTH_SHORT).show();
        }


    }
    private void showSensorList(List<Sensor> list){
        mySensorAdapter = new MySensorAdapter(this, list);
        /*if (!sensorListHasHeader) {
            sensorListView.addHeaderView(createHeader("ID","TYPE, VALUE"),null,false);
            sensorListHasHeader = true;
        }*/
        sensorListView.setAdapter(mySensorAdapter);
        sensorListView.setOnItemClickListener(this);
        showSensorsMode(true);
    }

    public void getSensorsfromServer(View view) {
        Log.d(LoginActivity.LOG, "ActivityListView. getSensorsfromServer");
        try {
            result = new MyDownTask("sensors/get",zone_id, this).execute().get();
            sensorList = gson.fromJson(result, tokenSensor.getType());
            showSensorList(sensorList);
            showSensorsMode(true);
        } catch (Exception e) {
            e.printStackTrace();
            showSensorsMode(false);
            Toast.makeText(this, "No data... :(",Toast.LENGTH_SHORT).show();
        }
        Log.d(LoginActivity.LOG, "!!!!!!!!  getSensorsfromServer result is = " + result);

    }

    private void showSensorsMode(boolean bool){
        Log.d(LoginActivity.LOG, "ActivityListView. showSensorsMode()="+bool);
        if(bool){
            sensorListView.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            zoneListView.setVisibility(View.GONE);
            buttonsRow.setVisibility(View.GONE);
            isSensorListVisible = true;

        }else {
            sensorListView.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            zoneListView.setVisibility(View.VISIBLE);
            buttonsRow.setVisibility(View.VISIBLE);
            isSensorListVisible = false;
        }
    }


    public void onClickShowMap(View view) {
        Log.d(LoginActivity.LOG, "ActivityListView. onClickShowMap");
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //if(i==0)return;
        //If Zonelist item clicked
        if(adapterView.getId()==zoneListView.getId()) {
            zone_id = zoneList.get(i).getId();
            showSensorsMode(true);
            getSensorsfromServer(null);

         // If Sensor list item clicked
        }else if(adapterView.getId()==sensorListView.getId()){
            curSensor = sensorList.get(i);
            Log.d(LoginActivity.LOG, "ActivityListView. onItemClick. curSensor = " + curSensor+" isSensorListVisible = "+isSensorListVisible);
            Intent intent = new Intent(this,DetailsActivity.class);
            startActivity(intent);
        }
    }


    private  void pushDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityListView.this);
        builder.setTitle("Exit ?")
                .setCancelable(false)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                            onBackPressed(true);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onBackPressed(boolean b){
        super.onBackPressed();
    }


    @Override
    public void onBackPressed() {
        if(isSensorListVisible){
            showSensorsMode(false);
        }else
           pushDialog();
    }

    public static List<Zone> getZoneList() {
        return zoneList;
    }

    public static List<Sensor> getSensorList() {
        return sensorList;
    }

    public static Sensor getCurSensor() {
        return curSensor;
    }
}

