package ua.kiev.netmaster.agro.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ua.kiev.netmaster.agro.R;
import ua.kiev.netmaster.agro.domain.Sensor;

public class DetailsActivity extends Activity {

    private Sensor sensor;
    private TextView id, zoneId, value, sensorId, type, created;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LoginActivity.LOG, "DetailsActivity. onCreate();");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detales);
        sensor = ActivityListView.getCurSensor();

        initViews();

    }
    private void initViews(){
        id = (TextView) findViewById(R.id.idDet);
        zoneId = (TextView) findViewById(R.id.zoneIdDet);
        value = (TextView) findViewById(R.id.valueDet);
        sensorId = (TextView) findViewById(R.id.sensorIdDet);
        type = (TextView) findViewById(R.id.typeDet);
        created = (TextView) findViewById(R.id.createdDet);

        id.setText(sensor.getId());
        zoneId.setText(sensor.getZone_id());
        value.setText(sensor.getValue());
        sensorId.setText(sensor.getSensor_id());
        type.setText(sensor.getType());
        created.setText(sensor.getCreated());

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onBackPressed(View v) {
        onBackPressed();
    }

}
