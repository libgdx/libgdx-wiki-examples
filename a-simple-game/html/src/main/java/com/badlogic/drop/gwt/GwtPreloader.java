package com.badlogic.drop.gwt;

import com.badlogic.drop.Preloader;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.preloader.Preloader.PreloaderCallback;
import com.badlogic.gdx.backends.gwt.preloader.Preloader.PreloaderState;

public class GwtPreloader implements Preloader {

    GwtApplication application;

    public GwtPreloader(GwtApplication application) {
        this.application = application;
    }

    @Override
    public void preloadBundle (final String bundle, final Callback callback) {
        application.getPreloader().preload(bundle + ".txt", new PreloaderCallback() {
            @Override
            public void update (PreloaderState state) {
                if (state.hasEnded()) {
                    callback.onBundlePreloaded(bundle);
                }
            }
            @Override
            public void error (String file) {
            }
        });
    }

}

