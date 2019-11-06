package com.cnb.projects.shuffledex.activity.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.cnb.projects.shuffledex.R;

/**
 * Created by vntcanb on 16/10/2015.
 */
public class LicenseDialog {

    public static void callLicenseDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View content = inflater.inflate(R.layout.license_dialog, null);

        new AlertDialogWrapper.Builder(context)
                .setView(content)
                .setPositiveButton("OK", null)
                .setTitle(R.string.license_dialog)
                .show();
    }
}
