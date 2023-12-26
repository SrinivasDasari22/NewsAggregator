package com.srinivas.newsaggregator;

import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class DownloadNews implements Runnable{
    private static RequestQueue queue;
    private final MainActivity mainActivity;


    public static String[] items = new String[300];
    public static final HashMap<String, ArrayList<String>> topicsHash = new HashMap<>();
    public static final HashMap<String, ArrayList<String>> languagesHash = new HashMap<>();

    public static final HashMap<String, ArrayList<String>> countriesHash = new HashMap<>();
    public static final HashMap<String, String> idHash = new HashMap<>();



    private static final String DATA_URL = "https://newsapi.org/v2/sources";
    private static final String api = "69e7d2d3f15d4b1ea724df0264ccab3c";
//3557b6d7be85490494ed0fe7b1b51812
    //69e7d2d3f15d4b1ea724df0264ccab3c

    DownloadNews(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public HashMap<String,ArrayList<String>> getTopics(){
        return topicsHash;
    }

    public HashMap<String,ArrayList<String>> getLanguages(){
        return languagesHash;
    }

    public HashMap<String,ArrayList<String>> getCountries(){
        return countriesHash;
    }


    private static void parseJSON(JSONObject response) throws JSONException {

        try {

            topicsHash.clear();
            countriesHash.clear();
            languagesHash.clear();
//            MainActivity.officialListItems.clear();

//        JSONObject jMain = response.getJSONObject("normalizedInput");

            JSONArray jsonArray = response.getJSONArray("sources");

            for (int i = 0; i < jsonArray.length() - 1; i++) {
                jsonArray.length();

                JSONObject jsobj = jsonArray.getJSONObject(i);
                String id = jsobj.getString("id");
                String name = jsobj.getString("name");
                String category = jsobj.getString("category");
                String language = jsobj.getString("language");
                String country = jsobj.getString("country");






                items[i] = name;

                //MainActivity.items[i] = name;
//                itemMain[i] = name;

//                MainActivity.items[i] = name;
//                ArrayList<String> n = new ArrayList<>();
                if(!topicsHash.containsKey(category)){
                    ArrayList<String> n = new ArrayList<>();
                    n.add(name);
                    topicsHash.put(category,n);
                } else{
                    ArrayList<String> tempList = topicsHash.get(category);
                    tempList.add(name);

                }

                if(!languagesHash.containsKey(language)){
                    ArrayList<String> n = new ArrayList<>();
                    n.add(name);
                    languagesHash.put(language,n);
                } else{
                    ArrayList<String> tempList1 = languagesHash.get(language);
                    tempList1.add(name);

                }
                if(!countriesHash.containsKey(country)){
                    ArrayList<String> n = new ArrayList<>();
                    n.add(name);
                    countriesHash.put(country,n);
                } else{
                    ArrayList<String> tempList2 = countriesHash.get(country);
                    tempList2.add(name);

                }

                idHash.put(name,id);




            }
//            System.out.println("Down ds :"+ items.length);
//
//
//
//            System.out.println("dsds1: "+topicsHash.keySet());
//            System.out.println("dsds2: "+countriesHash.keySet());
//            System.out.println("dsds3: "+languagesHash.keySet());
//
//            System.out.println("dsds: "+ MainActivity.items[1]);



//            mainActivity.updateData(items);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static String[] getItems() {

        return items;
    }


    @Override
    public void run() {
//        queue = Volley.newRequestQueue(mainActivity);

        Uri.Builder buildURL = Uri.parse(DATA_URL).buildUpon();
        buildURL.appendQueryParameter("q", "keyword");
        buildURL.appendQueryParameter("apikey", api);


        String urlToUse = buildURL.build().toString();
        System.out.println(urlToUse);

        try {
            URL url = new URL(urlToUse);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "News-App");
            connection.setRequestProperty("X-Api-Key", api);

            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return;
            }


            InputStream inputStream = connection.getInputStream();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            StringBuilder resultString = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {

                resultString.append(line);
            }

            inputStream.close();


            JSONTokener jsonTokener = new JSONTokener(resultString.toString());


            JSONObject jsonObject = new JSONObject(jsonTokener);


            parseJSON(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();

        }





    }



}
