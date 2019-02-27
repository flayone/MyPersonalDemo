package com.example.liyayu.myapplication.demoViews.bigImgDemo.glide;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shizhefei.view.largeimage.ILargeImageView;
import com.shizhefei.view.largeimage.factory.FileBitmapDecoderFactory;

import java.io.File;

/**
 * A base {@link com.bumptech.glide.request.target.Target} for displaying resources in
 * {@link ImageView}s.
 *
 * @param <> The type of resource that this target will display in the wrapped {@link ImageView}.
 */
public class LargeImageViewTarget extends ViewTarget<View, File>{
    private ILargeImageView largeImageView;
    public <V extends View & ILargeImageView> LargeImageViewTarget(V view) {
        super(view);
        this.largeImageView = view;
    }

    /**
     * Sets the given {@link Drawable} on the view using
     * {@link ImageView#setImageDrawable(Drawable)}.
     *
     * @param placeholder {@inheritDoc}
     */
    @Override
    public void onLoadStarted(Drawable placeholder) {
        super.onLoadStarted(placeholder);
        largeImageView.setImageDrawable(placeholder);
    }

    @Override
    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
        largeImageView.setImage(new FileBitmapDecoderFactory(resource));
    }

    /**
     * Sets the given {@link Drawable} on the view using
     * {@link ImageView#setImageDrawable(Drawable)}.
     *
     * @param errorDrawable {@inheritDoc}
     */
//    @Override
    public void onLoadFailed(Exception e, Drawable errorDrawable) {
        largeImageView.setImageDrawable(errorDrawable);
    }

//    @Override
//    public void onResourceReady(File resource, Transition<? super File> glideAnimation) {
//        largeImageView.setImage(new FileBitmapDecoderFactory(resource));
//    }

    /**
     * Sets the given {@link Drawable} on the view using
     * {@link ImageView#setImageDrawable(Drawable)}.
     *
     * @param placeholder {@inheritDoc}
     */
    @Override
    public void onLoadCleared(Drawable placeholder) {
        super.onLoadCleared(placeholder);
        largeImageView.setImageDrawable(placeholder);
    }
}

