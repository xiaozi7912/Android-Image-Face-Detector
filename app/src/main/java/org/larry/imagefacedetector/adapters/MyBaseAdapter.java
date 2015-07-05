package org.larry.imagefacedetector.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Larry on 2015/7/5.
 */
public class MyBaseAdapter extends BaseAdapter {
    protected final String LOG_TAG = getClass().getSimpleName();
    protected Activity mActivity = null;

    public MyBaseAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
