package com.cnb.projects.shuffledex.utils;

import android.os.Handler;
import android.os.Looper;

public class ApplicationData extends com.activeandroid.app.Application {

    /**
     * Static context to be accessible everywhere on the application.
     */
    private static ApplicationData sContext;

    /**
     * The application {@link Handler}
     */
    private Handler mHandler;

    /**
     * Default constructor.
     */
    public ApplicationData() {
        setAppContext(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setAppContext(this);
    }

    /**
     * Returns an application context.
     *
     * @return an application context.
     */
    public static ApplicationData getAppContext() {
        return sContext;
    }

    /**
     * Set the application context.
     *
     * @param context
     */
    private void setAppContext(ApplicationData context) {
        if (context != null && sContext == null) {
            sContext = context;
            sContext.mHandler = new Handler(Looper.getMainLooper());
        }
    }

    /**
     * Get an unique instance of application handler
     *
     * @return Application Handler.
     */
    public static Handler getHandler() {
        if (sContext.mHandler == null) {
            sContext.mHandler = new Handler(Looper.getMainLooper());
        }

        return sContext.mHandler;
    }

    /**
     * Return a localized string from the application's package's default string
     * table.
     *
     * @param resId String resource id.
     * @return {@link String}
     */
    public static String getResString(int resId) {
        return sContext.getString(resId);
    }

}