package com.pf.scoringsalud.user.Data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.io.InputStream;

public class LoadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    RoundedImageView roundedImageView;

    public LoadImage(ImageView ivImage){
        this.imageView = ivImage;
    }

    public LoadImage(RoundedImageView roundedImageView){
        this.roundedImageView = roundedImageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String url = strings[0];
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(roundedImageView != null){
            roundedImageView.setImageBitmap(bitmap);
        }else if(imageView!=null){
            imageView.setImageBitmap(bitmap);
        }


    }
}
