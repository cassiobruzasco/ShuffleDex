package com.cnb.projects.shuffledex.activity.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.cnb.projects.shuffledex.R;
import com.cnb.projects.shuffledex.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vntcanb on 30/10/2015.
 */
public class FilterDialog {

    public static Spinner mOrderSpinner;
    public static Spinner mFilterSpinner;

    public static void callFilterDialog(final Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View content = inflater.inflate(R.layout.filter_dialog, null);


        mOrderSpinner = (Spinner) content.findViewById(R.id.order_spinner);
        List<String> list = new ArrayList<String>();
        list.add("Pokemon ID");
        list.add("Attack Power");
        list.add("Stage");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (context, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        mOrderSpinner.setAdapter(dataAdapter);

        new AlertDialogWrapper.Builder(context)
                .setView(content)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (mOrderSpinner.getSelectedItemPosition()){
                            case 0:
                                Utils.SORT_OPTIONS = 0;
                                break;
                            case 1:
                                Utils.SORT_OPTIONS = 1;
                                break;
                            case 2:
                                Utils.SORT_OPTIONS = 2;
                                break;
                        }
                    }
                })
                .setTitle("Filter")
                .show();
    }
}
