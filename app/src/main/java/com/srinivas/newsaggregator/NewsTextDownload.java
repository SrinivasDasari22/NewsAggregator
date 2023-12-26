package com.srinivas.newsaggregator;

import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewsTextDownload {
    private static RequestQueue queue;
    private  MainActivity mainActivity;

    private static final String DATA_URL = "https://newsapi.org/v2/top-headlines";
    private static final String api ="69e7d2d3f15d4b1ea724df0264ccab3c";
//    69e7d2d3f15d4b1ea724df0264ccab3c


    public NewsTextDownload(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public  void getSourceData(String id) {

        RequestQueue queue = Volley.newRequestQueue(mainActivity);

        Uri.Builder buildURL = Uri.parse(DATA_URL).buildUpon();



        buildURL.appendQueryParameter("sources",id);
//    private final Object mainA;
        buildURL.appendQueryParameter("apikey", api);


        String urlToUse = buildURL.build().toString();
        System.out.println(urlToUse);


        Response.Listener<JSONObject> listener =
                response -> {
                    try {
                        parseJSON(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                };

        Response.ErrorListener error =
                error1 -> {
                    try {
                        parseJSON(null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                };


        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, urlToUse,
                        null, listener, error) { @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<>();
                            headers.put("User-Agent", "News-App");
                            headers.put("X-Api-Key", api);
                    return headers;
                }
                };
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
        //mainActivity.notifyAdapter();

    }

    private void parseJSON(JSONObject response) throws JSONException {

        try {

            MainActivity.officialNewsList.clear();

//        JSONObject jMain = response.getJSONObject("normalizedInput");

            JSONArray jsonArray = response.getJSONArray("articles");

//            System.out.println(jsonArray);

            for (int i = 0; i < jsonArray.length() - 1; i++) {
//                jsonArray.length();
                String author = "";
                String date = "";
                String title = "";
                String imageUrl = "";
                String description = "";
                String url="";



                JSONObject jsobj = jsonArray.getJSONObject(i);
                author = jsobj.getString("author");
                date = jsobj.getString("publishedAt");
                title = jsobj.getString("title");
                imageUrl = jsobj.getString("urlToImage");
                description = jsobj.getString("description");
                url = jsobj.getString("url");

                if(author.equals("") || author == null){
                    System.out.println("Location :"+ i );
                }

                MainActivity.officialNewsList.add(new News(author.trim(), date.trim(), title.trim(), imageUrl.trim(), description.trim(),url.trim()));

            }
            mainActivity.notifyAdapter();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
