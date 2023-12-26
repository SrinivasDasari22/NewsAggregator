package com.srinivas.newsaggregator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {


//    69e7d2d3f15d4b1ea724df0264ccab3c

    private DrawerLayout mDrawerLayout;
    private CharSequence parentMenu = "";
    private LayoutInflater mInflater;

    private static int filterToggle = 0;


    private ViewPager2 viewPager2;

    public static ArrayList<News> officialNewsList = new ArrayList<>();


    private static List<String> list = new ArrayList<String>();

    private NewsAdapter newsAdapter;

    private TextView textView;
    private ActionBarDrawerToggle mDrawerToggle;
    public static String[] items = new String[1000];

    public static String[] oriItems;
    private ListView mDrawerList;

    public NewsTextDownload newsTextDownload = new NewsTextDownload(this);


    ArrayList<String> country_codes = new ArrayList<>();

    ArrayList<String> language_codes = new ArrayList<>();

    ArrayList<String> topic_codes = new ArrayList<>();

    public static Map<String, String> countryCodes_hash_map = new HashMap<>();
    public static Map<String, String> languageCodes_hash_map = new HashMap<>();

    public static HashMap<String, ArrayList<String>> topicsHash = new HashMap<>();
    public static HashMap<String, ArrayList<String>> languagesHash = new HashMap<>();

    public static HashMap<String, ArrayList<String>> countriesHash = new HashMap<>();


    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        officialNewsList.clear();//
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //findViewById(R.id.const_layout).setBackground(ContextCompat.getDrawable(this, R.drawable.download));
//        ConstraintLayout constraintId  = findViewById(R.id.const_layout);
//        constraintId.setBackground(ContextCompat.getDrawable(this, R.drawable.download));


        viewPager2 = findViewById(R.id.view_pager);


        newsAdapter = new NewsAdapter(this, officialNewsList);
        viewPager2.setAdapter(newsAdapter);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        //textView = findViewById(R.id.textView);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.left_drawer);


        DownloadNews downloadNews = new DownloadNews(this);

        Thread thread = new Thread(downloadNews);

        thread.setName("download_thread");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        System.out.println("Cuurent thread name: "+thread.getName());


//        Thread thread = new Thread(downloadNews);
//        thread.start();


//        try {
////            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        try {
            loadDataFromFile();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        try {
//            downloadNews.getSourceData(this,items);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        items = downloadNews.getItems();

        list.clear();

        for (String s : items) {
            if (s != null && s.length() > 0) {
                list.add(s);
            }
        }


        oriItems = new String[downloadNews.getItems().length];


        oriItems = list.toArray(new String[list.size()]);


        topicsHash = downloadNews.getTopics();
        languagesHash = downloadNews.getLanguages();
        countriesHash = downloadNews.getCountries();

//        System.out.println("dsds aa: "+ MainActivity.items[1]);


//        System.out.println("items count :"+oriItems
//                .length);
        System.out.println("Down ds aa:" + oriItems.length);


        System.out.println("dsds1:aa " + topicsHash);
        System.out.println("dsds2 aa: " + countriesHash);
        System.out.println("dsds3 aa: " + languagesHash);

        System.out.println("dsds5 aa: " + countryCodes_hash_map);
        System.out.println("dsds6 aa: " + languageCodes_hash_map);







        updateAdapter();


//        mDrawerList.setAdapter(new ArrayAdapter<>(this,
//                    R.layout.drawer_list_item, oriItems));
////
//        mDrawerList.setOnItemClickListener(
//                (parent, view, position, id) -> selectItem(position));
//
////        System.out.println("dsds: "+ items[1]);
//
//
//        mDrawerToggle = new ActionBarDrawerToggle(this,                  /* host Activity */
//                    mDrawerLayout,         /* DrawerLayout object */
//                    R.string.drawer_open,  /* "open drawer" description for accessibility */
//                    R.string.drawer_close  /* "close drawer" description for accessibility */
//        );
//
//        if (getSupportActionBar() != null) {
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                getSupportActionBar().setHomeButtonEnabled(true);
//        }

    }


    public void notifyAdapter() {

        newsAdapter.notifyItemRangeChanged(0, officialNewsList.size());

    }


    public void updateAdapter() {


//        mDrawerList.setAdapter(new ArrayAdapter<>(this,
//                R.layout.drawer_item, oriItems));


        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.drawer_item, oriItems){

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view =super.getView(position, convertView, parent);

//                Log.d(TAG, "getView: Color ...  "+ position);

            TextView textView=(TextView) view.findViewById(R.id.drawer_ll);

            System.out.println( "main:   "+textView.getText().toString() +"pos "+position);


            ArrayList<String> at = new ArrayList<>(topicsHash.keySet()) ;

            int looper=0;
            for(looper =0;looper<at.size();looper++){
                ArrayList<String> temp = new ArrayList<>(topicsHash.get(at.get(looper)));


                for(int i =0;i<temp.size();i++){
                    if(temp.get(i).equalsIgnoreCase(textView.getText().toString())){
                        textView.setTextColor(getColor(at.get(looper)));

                        break;
                    }
//                    System.out.println(textView.toString() +" - "+ temp.get(0));
                }
//                if(temp.contains(textView.toString())){
//                    break;
//                }


            }


//            System.out.println("looper: "+ looper + " topic :" +at.get(looper-1));


            return view;
        }
    };
        mDrawerList.setAdapter(adapter);





//
        mDrawerList.setOnItemClickListener(
                (parent, view, position, id) -> selectItem(position));

//        System.out.println("dsds: "+ items[1]);


        mDrawerToggle = new ActionBarDrawerToggle(this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        );

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        setTitle("News Gateway ("+oriItems.length+")");
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView = Inflater.inflate(R.layout.drawer_item, parent, false);
//
//
//        final DrawerLayout layout = (DrawerLayout) convertView.findViewById(R.id.drawer_layout);
//
//        imageView.setImageResource(mIcons[position]);
//        textView.setText(mNavTitles[position]);
//
//        if (position == selectedPosition)
//            layout.setBackgroundColor(mContext.getResources().getColor(R.color.app_bg));
//        else
//            layout.setBackgroundColor(mContext.getResources().getColor(R.color.normal_bg));
//
//        return convertView;
//    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.menu = menu;

        menu.clear();





        SubMenu subMenu0 = menu.addSubMenu("Topics");
        SubMenu subMenu1 = menu.addSubMenu("Countries");
        SubMenu subMenu2 = menu.addSubMenu("languages");
        menu.add("Clear All");




        country_codes.add(0,"All");

        language_codes.add(0,"All");

        topic_codes.add(0,"All");



        country_codes.addAll(countriesHash.keySet());


        Collections.sort(country_codes);
        System.out.println("sb co"+country_codes);

        topic_codes.addAll(topicsHash.keySet());

        Collections.sort(topic_codes);
        System.out.println("sb to"+topic_codes);

        language_codes.addAll(languagesHash.keySet());

        Collections.sort(language_codes);
        System.out.println("sb to"+language_codes);

        subMenu0.add("All");

        subMenu1.add("All");
        subMenu2.add("All");


        for(int j = 1;j<topic_codes.size();j++){
//            subMenu0.add(0,j,j,topic_codes.get(j));
//            MenuItem item = subMenu0.getItem(j);
            SpannableString s = new SpannableString(topic_codes.get(j));
            s.setSpan(new ForegroundColorSpan(getColor(topic_codes.get(j))), 0, s.length(), 0);
            subMenu0.add(s);
//            item.setTitle(s);

        }
        for (int j = 1; j < country_codes.size(); j++) {
//            System.out.println("country code debug :"+countryCodes_hash_map.get(country_codes.get(j).toUpperCase(Locale.ROOT)));
//            System.out.println("country code debug 1:"+country_codes.get(j).toUpperCase(Locale.ROOT));

            subMenu1.add(1, j, j,countryCodes_hash_map.get(country_codes.get(j).toUpperCase(Locale.ROOT)));
            System.out.println(countryCodes_hash_map.get(country_codes.get(j)));
        }
        for (int j = 1; j < language_codes.size(); j++) {
            subMenu2.add(2, j, j,languageCodes_hash_map.get(language_codes.get(j).toUpperCase(Locale.ROOT)));
        }

        System.out.println("topid:"+ topic_codes);

//        getMenuInflater().inflate(R.menu.action_menu, menu);



        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        // Check drawer first!
        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            Log.d(TAG, "onOptionsItemSelected: mDrawerToggle " + item);
            return true;
        }


        if (item.hasSubMenu()){
            parentMenu = item.getTitle();
            return true;}

        CharSequence titleSelected = item.getTitle();
        StringBuilder sb = new StringBuilder(titleSelected.length());
        sb.append(titleSelected);


//        System.out.println(("Items selected : "+ parentMenu.toString()+","+item));

        if(item.toString().equalsIgnoreCase("Clear All")){

            filterToggle = 0;
            parentMenu="";
            list.clear();
            for(String s : items) {
                if(s != null && s.length() > 0) {
                    list.add(s);
                }
            }
            oriItems = list.toArray(new String[list.size()]);
            System.out.println("updated list: "+ oriItems.length);


        }

         if(parentMenu.toString().equalsIgnoreCase("TOPICS") ){

             filterToggle = 1;

             ArrayList<String> topicTemp = new ArrayList();
            List<String> topicList = new ArrayList<String>();
             ArrayList <ArrayList<String>> topicsFullArray = new ArrayList<>();


             if(item.toString().equalsIgnoreCase("all")){
                topicsFullArray.addAll(topicsHash.values());

                for(ArrayList<String> s: topicsFullArray){
                    topicTemp.addAll(s);
                }
//                 topicTemp = new ArrayList<String>(Arrays.asList(topicsFullArray));
//                 System.out.println(topicsFullArray);

            } else{
                topicTemp = topicsHash.get(item.toString());

            }

            for(String s : topicTemp) {

                if(s != null && s.length() > 0) {
                    topicList.add(s);
                }
            }

//            oriItems = new String[topicList.size()];
            oriItems = topicList.toArray(new String[topicList.size()]);

            System.out.println("updated list: "+ oriItems.length);

        } else if(parentMenu.toString().equalsIgnoreCase("COUNTRIES")){


             filterToggle = 2;
             ArrayList<String> conTemp = new ArrayList<>();
            List<String> conList = new ArrayList<String>();

             ArrayList <ArrayList<String>> countriesFullArray = new ArrayList<>();


             if(item.toString().equalsIgnoreCase("all")){
                 countriesFullArray.addAll(countriesHash.values());

                 for(ArrayList<String> s: countriesFullArray){
                     conTemp.addAll(s);
                 }
//

             } else {
                 ;
//            System.out.println("arraylist :"+countriesHash.get(getKeyFromHash(countryCodes_hash_map,item).toLowerCase(Locale.ROOT)));
                 conTemp.addAll((countriesHash.get(getKeyFromHash(countryCodes_hash_map, item).toLowerCase(Locale.ROOT))));
             }
            for(String s : conTemp) {

                if(s != null && s.toString().length() > 0) {
                    conList.add(s.toString());
                }
            }

            oriItems = conTemp.toArray(new String[conList.size()]);
            System.out.println("updated list: "+ oriItems.length);





        } else if(parentMenu.toString().equalsIgnoreCase("LANGUAGES")){


             filterToggle = 3;


             ArrayList<String> lanTemp = new ArrayList<>();
            List<String> lanList = new ArrayList<String>();

             ArrayList <ArrayList<String>> languagesFullArray = new ArrayList<>();


             if(item.toString().equalsIgnoreCase("all")){
                 languagesFullArray.addAll(languagesHash.values());

                 for(ArrayList<String> s: languagesFullArray){
                     lanTemp.addAll(s);
                 }
//

             } else {

                 lanTemp.addAll(languagesHash.get(getKeyFromHash(languageCodes_hash_map, item).toLowerCase(Locale.ROOT)));
             }
            for(String s : lanTemp) {

                if(s != null && s.length() > 0) {
                    lanList.add(s);
                }
            }

            oriItems = lanList.toArray(new String[lanList.size()]);
            System.out.println("updated list: "+ oriItems.length);




        }
//        else if(parentMenu.toString().equalsIgnoreCase("CLEAR ALL")){
//
//
//
//
//            list.clear();
//            for(String s : items) {
////
//                if(s != null && s.length() > 0) {
//                    list.add(s);
//                }
//            }
//
//            oriItems = list.toArray(new String[list.size()]);
//            System.out.println("updated list: "+ oriItems.length);
//
//
//        }

      updateAdapter();

        newsAdapter = new NewsAdapter(this, officialNewsList);
        viewPager2.setAdapter(newsAdapter);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);



//        return true;

        return super.onOptionsItemSelected(item);
    }


    public String getKeyFromHash(Map<String,String> map,MenuItem item){
        for (Map.Entry<String,String> entry : map.entrySet()) {
            if(entry.getValue().equalsIgnoreCase(item.toString())){
                System.out.println(entry.getKey());
                return entry.getKey();
            }

//
        }

        return null;
    }

    private void selectItem(int position) {


        String text =oriItems[position];
        String id = DownloadNews.idHash.get(text);
        setTitle(text);
        //findViewById(R.id.const_layout).setBackground(ContextCompat.getDrawable(this, R.drawable.white));

        findViewById(R.id.drawer_layout).setBackgroundResource(0);
        viewPager2.setCurrentItem(0);


        newsTextDownload.getSourceData(id);

        mDrawerLayout.closeDrawer(findViewById(R.id.c_layout));
    }



    private void loadDataFromFile() throws JSONException {

        InputStream countryCodesInputStream;
        InputStream languageCodesInputStream;

        try {
            languageCodesInputStream = getResources().openRawResource(getResources().getIdentifier("language_codes", "raw", getPackageName()));
            countryCodesInputStream = getResources().openRawResource(getResources().getIdentifier("country_codes", "raw", getPackageName()));

            String cs = readInputStream(countryCodesInputStream);
            String ls = readInputStream(languageCodesInputStream);

            JSONTokener countryTokener = new JSONTokener(cs);
            JSONTokener languageTokener = new JSONTokener(ls);

            JSONObject countryObject = new JSONObject(countryTokener);
            JSONObject languageObject = new JSONObject(languageTokener);

            JSONArray countriesJsonArray = countryObject.getJSONArray("countries");
            JSONArray languagesJsonArray = languageObject.getJSONArray("languages");

            for(int i = 0 ; i < countriesJsonArray.length() ; i++){
//                country_codes.add(countriesJsonArray.getJSONObject(i).getString("name"));
                countryCodes_hash_map.put(countriesJsonArray.getJSONObject(i).getString("code"),countriesJsonArray.getJSONObject(i).getString("name"));
            }
            for(int j = 0 ; j < languagesJsonArray.length() ; j++){
//                language_codes.add(countriesJsonArray.getJSONObject(j).getString("name"));
                languageCodes_hash_map.put(languagesJsonArray.getJSONObject(j).getString("code"),languagesJsonArray.getJSONObject(j).getString("name"));
            }

            System.out.println("county hash map "+countryCodes_hash_map);
            System.out.println("lang hash map "+languageCodes_hash_map);

//            Log.d(TAG, "loadDataFromFile: Countries Hashmap " + countryCodes_hash_map);
//            Log.d(TAG, "loadDataFromFile: Languages Hashmap " + languageCodes_hash_map);
        } catch (Exception e) {
//            Log.d(TAG, "loadDataFromFile: " + e.getMessage());
            e.printStackTrace();
            return;
        }



    }


    public String readInputStream(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

//    public void updateData(String[] items1) {
//
//        for (int i = 0; i < items1.length; i++) {
////            officialNewsList.add("Drawer Item #" + (i + 1));
//            items[i] = items1[i];
////            System.out.println("ds:" + items[i]);
//        }

//        items = items1;
//        System.out.println("ud: "+ items[0]);
//
//    }

    public void downloadFailed() {

//        items = new String[1000];
    }

    public boolean isEmptyStringArray(String [] array){
        for(int i=0; i<array.length; i++){
            if(array[i]!=null){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        this.finish();
    }

    public static Integer getColor(String category){
        if(category.trim().equals("business")){
            return Color.BLUE;
        }
        else if(category.trim().equals("entertainment")){
            return Color.RED;
        }
        else if(category.trim().equals("general")){
            return Color.GREEN;
        }
        else if(category.trim().equals("health")){
            return Color.BLACK;
        }
        else if(category.trim().equals("science")){
            return Color.YELLOW;
        }
        else if(category.trim().equals("sports")){
            return Color.CYAN;
        }
        else if(category.trim().equals("technology")){
            return Color.GRAY;
        }
        return Color.WHITE;
    }

}