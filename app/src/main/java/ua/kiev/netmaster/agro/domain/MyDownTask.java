package ua.kiev.netmaster.agro.domain;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.URL;

import ua.kiev.netmaster.agro.activities.LoginActivity;


/**
 * Created by ПК on 06.08.2015.
 */
public class MyDownTask extends AsyncTask<Void, Void, String>{

    private Gson gson;

    private String login, zone_id, password, urlStr = "http://agromonitor.mechatroniclab.com/api/";

    private String result;

    //private Long orderId;

    public MyDownTask(String urlTeil, String login, String password) {
       urlStr += urlTeil;
       this.login = login;
       this.password = password;

        gson = new Gson();

    }

    public MyDownTask(String urlStrTail,String zoneId) {
        this.urlStr+= urlStrTail;
        this.zone_id = zoneId;
        gson = new Gson();
    }

    public MyDownTask(String urlStrTail) {
        this.urlStr+= urlStrTail;
        gson = new Gson();
    }

    protected void onPreExecute() {}

    @Override
    protected String doInBackground(Void... params) {
        Log.d(LoginActivity.LOG, "MyDownTask. doInBackground");
        return connect();
    }

    protected void onPostExecute(String result) {
    }

    public String connect() {

        CookieHandler.getDefault();

        StringBuilder responses = new StringBuilder();
        URL url;
        HttpURLConnection con = null;
        String inputLine;
        try {
            Log.d(LoginActivity.LOG, "urlStr= "+urlStr);
            url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);

            if((login != null && password != null)|(zone_id!=null)) {
                Uri.Builder builder = new Uri.Builder();
                if (login != null && password != null) {
                    Log.d(LoginActivity.LOG, "connect(). login != null && password != null");
                    builder.appendQueryParameter("login", login)
                            .appendQueryParameter("password", password);
                } else if (zone_id != null) {
                    Log.d(LoginActivity.LOG, "connect(). zone_id != null");
                    builder.appendQueryParameter("zone_ids", zone_id);
                }

                String query = builder.build().getEncodedQuery();

                Log.d(LoginActivity.LOG, "connect(). String query = "+query);
                OutputStream os = con.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
            }
            con.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                responses.append(inputLine);
                //Log.d(LoginActivity.LOG, inputLine);
                //System.out.println(inputLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        Log.d(LoginActivity.LOG, "MyDownTask. responses.toString() = "+responses.toString());

        result = prepareToParseToGson(responses.toString());

        return result;
    }

    private String prepareToParseToGson(String input) {
        int startChar = input.indexOf('[');
        int endChar = input.indexOf(']');

        if(startChar>0) {
            String res = input.substring(startChar, endChar + 1);
            Log.d(LoginActivity.LOG, "ActivityListView after prepareToParseToGson: " + res);
            return res;
        }else
            return input;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlStr() {
        return urlStr;
    }

    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }

    public String getResalt() {
        return result;
    }

    public void setResalt(String resalt) {
        this.result = resalt;
    }
}

