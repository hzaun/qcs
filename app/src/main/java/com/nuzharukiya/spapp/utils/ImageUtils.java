package com.nuzharukiya.spapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.nuzharukiya.spapp.R;

/**
 * Created by Nuzha Rukiya on 18/06/08.
 */
public class ImageUtils {

    private Context context;

    public ImageUtils(Context context) {
        this.context = context;
    }

    public void loadImage(String sUrl, ImageView imageView){
        Glide.with(context)
                .load(sUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.progress_animation)
                        .centerCrop())
                .transition(new DrawableTransitionOptions()
                        .crossFade())
                .into(imageView);
    }
}
