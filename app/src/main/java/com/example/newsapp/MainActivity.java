package com.example.newsapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    ListView listNews;
    ProgressBar loader;

    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    static final String KEY_ID = "id";
    static final String KEY_TYPE = "type";
    static final String KEY_SECTIONID = "sectionId";
    static final String KEY_SECTIONNAME = "sectionName";
    static final String KEY_WEBTITLE = "webTitle";
    static final String KEY_WEBURL = "webUrl";
    static final String KEY_APIURL = "apiUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listNews = (ListView) findViewById(R.id.the_list_of_news);
        loader = (ProgressBar) findViewById(R.id.progress);
        listNews.setEmptyView(loader);



        if(NewsApp.isNetworkAvailable(getApplicationContext()))
        {
            DownloadNews newsTask = new DownloadNews();
            newsTask.execute();
        }else{
            Toast.makeText(getApplicationContext(), "there is no internet connection", Toast.LENGTH_LONG).show();
        }

    }


    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected String doInBackground(String... args) {
            String xml = "";

            String urlParameters = "";
            xml = NewsApp.excuteGet("https://content.guardianapis.com/tags?section=news&q=news&api-key=b8aec300-5f57-4ee8-9f90-e2073b4f802d", urlParameters);
            return  xml;
        }
        @Override
        protected void onPostExecute(String xml) {

            if(xml.length()>10){

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_ID, jsonObject.optString(KEY_ID).toString());
                        map.put(KEY_SECTIONNAME, jsonObject.optString(KEY_SECTIONNAME).toString());
                        map.put(KEY_APIURL, jsonObject.optString(KEY_APIURL).toString());
                        map.put(KEY_TYPE, jsonObject.optString(KEY_TYPE).toString());
                        map.put(KEY_SECTIONID, jsonObject.optString(KEY_SECTIONID).toString());
                        map.put(KEY_WEBURL, jsonObject.optString(KEY_WEBURL).toString());
                        map.put(KEY_WEBTITLE, jsonObject.optString(KEY_WEBTITLE).toString());
                        dataList.add(map);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "there is an wrong", Toast.LENGTH_SHORT).show();
                }

                NewsAppAdapter adapter = new NewsAppAdapter(MainActivity.this, dataList);
                listNews.setAdapter(adapter);

                listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
                        intent.putExtra("url", dataList.get(+position).get(KEY_WEBURL));
                        startActivity(intent);
                    }
                });

            }else{
                Toast.makeText(getApplicationContext(), "No new news", Toast.LENGTH_SHORT).show();
            }
        }



    }



}