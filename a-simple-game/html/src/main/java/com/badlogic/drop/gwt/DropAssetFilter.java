package com.badlogic.drop.gwt;

import com.badlogic.gdx.backends.gwt.preloader.DefaultAssetFilter;

public class DropAssetFilter extends DefaultAssetFilter {
    @Override
    public String getBundleName(String file) {
        file = file.replace('\\', '/');
        if (file.contains("delayed-loading/")) return "delayed-loading";
        return super.getBundleName(file);
    }
}
