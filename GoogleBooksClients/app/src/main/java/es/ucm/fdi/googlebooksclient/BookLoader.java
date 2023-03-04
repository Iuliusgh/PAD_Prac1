package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {

    private String mQueryString, mPrintType;

    public BookLoader(@NonNull Context context, String queryString, String printType) {
        super(context);
        mQueryString = queryString;
        mPrintType = printType;
    }

    @Nullable
    @Override
    public List<BookInfo> loadInBackground() {
        return BookInfo.fromJsonResponse(NetworkUtils.getBookInfoJson(this.mQueryString, this.mPrintType));
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
