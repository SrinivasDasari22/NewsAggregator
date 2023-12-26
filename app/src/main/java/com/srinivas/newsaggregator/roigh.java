//package com.srinivas.newsaggregator;
//
//import android.net.Uri;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class DownloadNews implements Runnable{
//    private static RequestQueue queue;
//    private final MainActivity mainActivity;
//
//    public  static final String[] items = new String[1000];
//    public static final HashMap<String, ArrayList<String>> topicsHash = new HashMap<>();
//    public static final HashMap<String, ArrayList<String>> languagesHash = new HashMap<>();
//
//    public static final HashMap<String, ArrayList<String>> countriesHash = new HashMap<>();
//
//
//    private static final String DATA_URL = "https://newsapi.org/v2/sources";
//    private static final String api = "69e7d2d3f15d4b1ea724df0264ccab3c";
//
//
//    DownloadNews(MainActivity mainActivity){
//        this.mainActivity = mainActivity;
//    }
//
//    public void  getSourceData() {
//
//
////        mainActivity = mainActivity1;
////        MainActivity.items = new String[1000];
//
//
////        MainActivity.officialNewsList.clear();
////        queue = Volley.newRequestQueue(mainActivity);
////
////        Uri.Builder buildURL = Uri.parse(DATA_URL).buildUpon();
////        buildURL.appendQueryParameter("q", "keyword");
////        buildURL.appendQueryParameter("apikey", api);
////
////
////        String urlToUse = buildURL.build().toString();
////        System.out.println(urlToUse);
////
////        Response.Listener<JSONObject> listener =
////                response -> {
////                    try {
////                        parseJSON(response);
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                };
////
////        Response.ErrorListener error =
////                error1 -> {
////                    try {
////                        parseJSON(null);
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                };
////
////
////        JsonObjectRequest jsonObjectRequest =
////                new JsonObjectRequest(Request.Method.GET, urlToUse,
////                        null, listener, error) {
////                    @Override
////                    public Map<String, String> getHeaders() throws AuthFailureError {
////                        Map<String, String> headers = new HashMap<>();
////                        headers.put("User-Agent", "News-App");
////                        headers.put("X-Api-Key", api);
////                        return headers;
////                    }
////                };
////        // Add the request to the RequestQueue.
////        queue.add(jsonObjectRequest);
////        //mainActivity.notifyAdapter();
//
//
//
//
//    }
//    public HashMap<String,ArrayList<String>> getTopics(){
//        return topicsHash;
//    }
//
//    public HashMap<String,ArrayList<String>> getLanguages(){
//        return languagesHash;
//    }
//
//    public HashMap<String,ArrayList<String>> getCountries(){
//        return countriesHash;
//    }
//
//
//    private static void parseJSON(JSONObject response) throws JSONException {
//
//        try {
//
//            topicsHash.clear();
//            countriesHash.clear();
//            languagesHash.clear();
//
////        JSONObject jMain = response.getJSONObject("normalizedInput");
//
//            JSONArray jsonArray = response.getJSONArray("sources");
//
//            for (int i = 0; i < jsonArray.length() - 1; i++) {
//                jsonArray.length();
//
//                JSONObject jsobj = jsonArray.getJSONObject(i);
//                String id = jsobj.getString("id");
//                String name = jsobj.getString("name");
//                String category = jsobj.getString("category");
//                String language = jsobj.getString("language");
//                String country = jsobj.getString("country");
//
//
//                items[i] = name;
//
//                //MainActivity.items[i] = name;
////                itemMain[i] = name;
//
////                MainActivity.items[i] = name;
////                ArrayList<String> n = new ArrayList<>();
//                if(!topicsHash.containsKey(category)){
//                    ArrayList<String> n = new ArrayList<>();
//                    n.add(name);
//                    topicsHash.put(category,n);
//                } else{
//                    ArrayList<String> tempList = topicsHash.get(category);
//                    tempList.add(name);
//
//                }
//
//                if(!languagesHash.containsKey(language)){
//                    ArrayList<String> n = new ArrayList<>();
//                    n.add(name);
//                    languagesHash.put(language,n);
//                } else{
//                    ArrayList<String> tempList1 = languagesHash.get(language);
//                    tempList1.add(name);
//
//                }
//                if(!countriesHash.containsKey(country)){
//                    ArrayList<String> n = new ArrayList<>();
//                    n.add(name);
//                    countriesHash.put(country,n);
//                } else{
//                    ArrayList<String> tempList2 = countriesHash.get(country);
//                    tempList2.add(name);
//
//                }
//
//
//
//
//
//            }
//            System.out.println("Down ds :"+ items.length);
//
//
//
//            System.out.println("dsds1: "+topicsHash.keySet());
//            System.out.println("dsds2: "+countriesHash.keySet());
//            System.out.println("dsds3: "+languagesHash.keySet());
//
//            System.out.println("dsds: "+ MainActivity.items[1]);
//
//
//
////            mainActivity.updateData(items);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//    public static String[] getItems() {
//
//        return items;
//    }
//
//
//    @Override
//    public void run(){
//        queue = Volley.newRequestQueue(mainActivity);
//
//        Uri.Builder buildURL = Uri.parse(DATA_URL).buildUpon();
//        buildURL.appendQueryParameter("q", "keyword");
//        buildURL.appendQueryParameter("apikey", api);
//
//
//        String urlToUse = buildURL.build().toString();
//        System.out.println(urlToUse);
//
//        Response.Listener<JSONObject> listener =
//                response -> {
//                    try {
//                        parseJSON(response);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                };
//
//        Response.ErrorListener error =
//                error1 -> {
//                    try {
//                        parseJSON(null);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                };
//
//
//        JsonObjectRequest jsonObjectRequest =
//                new JsonObjectRequest(Request.Method.GET, urlToUse,
//                        null, listener, error) {
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> headers = new HashMap<>();
//                        headers.put("User-Agent", "News-App");
//                        headers.put("X-Api-Key", api);
//                        return headers;
//                    }
//                };
//        // Add the request to the RequestQueue.
//        queue.add(jsonObjectRequest);
//        //mainActivity.notifyAdapter();
//
//        System.out.println("run iun download");
//    }
//}
