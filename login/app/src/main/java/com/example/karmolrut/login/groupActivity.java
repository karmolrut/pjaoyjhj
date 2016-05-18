package com.example.karmolrut.login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by karmolrut on 5/4/2559.
 */
public class groupActivity extends Activity  {
    private String jsonResult;
    public String sID;
    ArrayList<String> myArrList = new ArrayList<String>();
    private String url = "http://activityen.azurewebsites.net/put_groupactivity.php";
    private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupactivity);
        listView = (ListView) findViewById(R.id.listView2);
        accessWebService();
    }

    // Async Task to access the web
    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
            }

            catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            }

            catch (IOException e) {
                // e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Error..." + e.toString(), Toast.LENGTH_LONG).show();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            ListDrwaer();
        }
    }// end async task

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        // passes values for the urls string array
        task.execute(new String[] { url });
    }

    // build hash set for list view
    public void ListDrwaer() {
        List<Map<String, String>> GroupList = new ArrayList<Map<String, String>>();

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("groupactivityy");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                //sID = jsonChildNode.optString("G_ID");
                myArrList.add(jsonChildNode.optString("G_ID"));
                String name = jsonChildNode.optString("G_NAME");
                String number = jsonChildNode.optString("G_YEAR_CREATE");
                String outPut = name + "  " + number;
                GroupList.add(createGroup("putGA", outPut));
            }

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Error" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, GroupList,
                android.R.layout.simple_list_item_1,
                new String[] { "putGA" }, new int[] { android.R.id.text1 });
        listView.setAdapter(simpleAdapter);
        //Toast.makeText(getApplication(), "c", Toast.LENGTH_SHORT).show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                //String selected = (String) ((TextView) view).getText();
                //Toast.makeText(getApplicationContext(),
                //       "zzzzz", Toast.LENGTH_SHORT).show();
                // addnoti1();

                for (int i = 0; i < myArrList.size(); i++) {
                    // myArrList.get(i))
                    //System.out.println("BITCH "+ myArrList.get(i).toString());
                }

                Bundle b = new Bundle();
                b.putString("valVar", myArrList.get(position).toString());
                view.getContext().startActivity(new Intent(view.getContext(), Activitys.class).putExtras(b));
            }
        });
    }

    private HashMap<String, String> createGroup(String name, String number) {
        HashMap<String, String> putgroup = new HashMap<String, String>();
        putgroup.put(name, number);
        return putgroup;
    }


}
