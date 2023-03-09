package es.ucm.fdi.googlebooksclient;


import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String TAG = "NetworkUtils";
    private static final String BASE_URL =
            "https://www.googleapis.com/books/v1/volumes?";
    private static final String QUERY_PARAM = "q";
    private static final String MAX_RESULTS = "maxResults";
    private static final String PRINT_TYPE = "printType";

    static String getBookInfoJson(String queryString, String printType){
        HttpURLConnection conn = null;
        InputStream is = null;
        String res = null;
        Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(MAX_RESULTS, "40")
                .appendQueryParameter(PRINT_TYPE, printType)
                .build();
        try {
            URL requestURL = new URL(builtURI.toString());
            conn = (HttpURLConnection) requestURL.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int response = conn.getResponseCode();
            Log.i(TAG, "The response is: " + response);

            is = conn.getInputStream();
            if(is == null){
                return null;
            }
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            res = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.i(TAG, "The result is:" + res);
            return res;
        }
    }
}
