package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BooksResultListAdapter extends RecyclerView.Adapter<BooksResultListAdapter.ViewHolder>{
    private List<BookInfo> mBooksData;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtTitulo, txtAutor, txtLink;
        private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.titulo);
            txtAutor = itemView.findViewById(R.id.autor);
            txtLink = itemView.findViewById(R.id.link);
            image = itemView.findViewById(R.id.image);
            txtLink.setVisibility(View.INVISIBLE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(txtLink.getText().toString()));
            view.getContext().startActivity(browserIntent);
        }
    }
    public BooksResultListAdapter(List<BookInfo> booksData, Context context){
        this.mBooksData = booksData;
        this.mContext = context;

    }
    @NonNull
    @Override
    public BooksResultListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksResultListAdapter.ViewHolder holder, int position) {
        holder.txtAutor.setText(mBooksData.get(position).getmAuthors());
        holder.txtTitulo.setText(mBooksData.get(position).getmTitle());
        holder.txtLink.setText(mBooksData.get(position).getmInfoLink().toString());
        if(mBooksData.get(position).getmImageLink() != null){
            holder.image.setImageBitmap(mBooksData.get(position).getmImageLink());
        }
        else{
            holder.image.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mBooksData != null ? mBooksData.size() : 0;
    }

    public void setBooksData(List<BookInfo> data){
        this.mBooksData = data;
    }
}
