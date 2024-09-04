package com.badlogic.drop.lwjgl3;

import com.badlogic.drop.Preloader;

public class NullPreloader implements Preloader {

    @Override
    public void preloadBundle (final String bundle, final Callback callback) {
        callback.onBundlePreloaded(bundle);
    }

}
