package com.cnb.projects.shuffledex.activity.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.cnb.projects.shuffledex.R;

/**
 * Created by vntcanb on 08/10/2015.
 */
public class AboutDialog {

    public static void callAboutDialog(Context context){

        LayoutInflater inflater = LayoutInflater.from(context);
        View content = inflater.inflate(R.layout.about_dialog, null);

        new AlertDialogWrapper.Builder(context)
                .setView(content)
                .setPositiveButton("OK",null)
                .setTitle(R.string.about_dialog)
                .show();
    }
}
