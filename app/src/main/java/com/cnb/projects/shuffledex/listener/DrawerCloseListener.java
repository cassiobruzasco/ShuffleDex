package com.cnb.projects.shuffledex.listener;

import android.support.v4.widget.DrawerLayout;

/**
 * Created by vntcanb on 26/10/2015.
 */
public class DrawerCloseListener {

    public interface CloseDrawer{
        void onLoad(DrawerLayout drawerLayout);
    }
}
