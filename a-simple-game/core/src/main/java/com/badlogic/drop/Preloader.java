package com.badlogic.drop;

public interface Preloader {

    /**Request that the named bundle be preloaded.
     *
     * @param bundle the name of the bundle
     * @param callback to be called upon preload completion
     */
    public void preloadBundle(String bundle, Callback callback);

    /** Allows the Preloader to communicate back to the caller. */
    public interface Callback {

        /**Called when the named bundle has been preloaded.
         *
         * @param bundle the name of the bundle
         */
        void onBundlePreloaded(String bundle);

    }
}
