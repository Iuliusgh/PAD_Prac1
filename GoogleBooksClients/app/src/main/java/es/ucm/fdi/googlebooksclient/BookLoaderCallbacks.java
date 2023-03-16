package es.ucm.fdi.googlebooksclient;

import static es.ucm.fdi.googlebooksclient.MainActivity.updateBooksResultList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

public class BookLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<BookInfo>>{
    public static final String EXTRA_QUERY = "queryString";
    public static final String EXTRA_PRINT_TYPE = "printType";
    private Context mContext;
    public BookLoaderCallbacks(Context context){
        mContext = context;
    }
    @NonNull
    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, @Nullable Bundle args) {
        return new BookLoader(mContext, args.getString(EXTRA_QUERY), args.getString(EXTRA_PRINT_TYPE));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<BookInfo>> loader, List<BookInfo> data) {
        ((MainActivity) mContext).updateBooksResultList(data);
        if(data != null){
            ((MainActivity) mContext).mResult.setText(R.string.results);
        }
        else{
            ((MainActivity) mContext).mResult.setText(R.string.no_result);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<BookInfo>> loader) {

    }

}
