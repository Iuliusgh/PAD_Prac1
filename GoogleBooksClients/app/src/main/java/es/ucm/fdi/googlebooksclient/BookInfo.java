package es.ucm.fdi.googlebooksclient;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookInfo {
    private String mTitle, mAuthors;
    private URL mInfoLink;

    private static final String TAG = "BookInfo";
    public BookInfo(String t, String a, URL i){
        this.mTitle = t;
        this.mAuthors = a;
        this.mInfoLink = i;
    }
    static List<BookInfo> fromJsonResponse(String s){
        List<BookInfo> res = new ArrayList<BookInfo>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray listOfItems = (JSONArray) jsonObject.get("items");
            for(int i = 0; i < listOfItems.length(); i++){
                JSONObject book = listOfItems.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                String title = (String) volumeInfo.get("title");
                URL link = new URL((String) volumeInfo.get("infoLink"));
                String authors = "";
                if(volumeInfo.has("authors")){
                    JSONArray listOfAuthors = (JSONArray) volumeInfo.get("authors");
                    if(listOfAuthors.length() > 0){
                        authors += listOfAuthors.get(0).toString();
                    }
                    for(int j = 1; j < listOfAuthors.length(); j++){
                        authors += ", " + listOfAuthors.get(j).toString();
                    }
                }
                else{
                    authors = "No tiene autores";
                }
                Log.i(TAG, "Title: " + title);
                Log.i(TAG, "Authors: " + authors);
                Log.i(TAG, "Link: " + link.toString());
                res.add(new BookInfo(title, authors, link));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthors() {
        return mAuthors;
    }

    public URL getmInfoLink() {
        return mInfoLink;
    }
}
