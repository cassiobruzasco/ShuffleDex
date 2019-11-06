package com.cnb.projects.shuffledex.task;

import android.os.AsyncTask;

import com.cnb.projects.shuffledex.model.Pokemon;

/**
 * Created by vntcanb on 04/11/2015.
 */
public class CountDatabase extends AsyncTask<Integer, Void, Integer>  {

    private CountDataListener mListener;

    public CountDatabase(final CountDataListener listener) {
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        int counter=0;

        switch(params[0]) {
            case 0:
                counter = Pokemon.selectCounterCaptured();
                break;
            case 1:
                counter = Pokemon.selectCounterS();
                break;
            case 2:
                counter = Pokemon.selectMissingS();
                break;
        }

        return counter;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(mListener!=null){
            mListener.onCounterFinished(integer);
        }
    }

    public interface CountDataListener {
        void onCounterFinished(final int counter);
    }
}
