package org.larry.imagefacedetector.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.larry.imagefacedetector.R;
import org.larry.imagefacedetector.models.FolderModel;

import java.util.ArrayList;

/**
 * Created by Larry on 2015/7/5.
 */
public class FolderAdapter extends MyBaseAdapter {
    private ArrayList<FolderModel> mDataList = null;

    public FolderAdapter(Activity activity, ArrayList<FolderModel> dataList) {
        super(activity);
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        ViewHolder holder = null;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_folder, null);
            holder = new ViewHolder();
            holder.folderName = (TextView) convertView.findViewById(R.id.item_folder_name);
            convertView.setTag(holder);
        }

        FolderModel selectedItem = mDataList.get(position);
        holder.folderName.setText(selectedItem.fileName);
        return convertView;
    }

    class ViewHolder {
        public TextView folderName = null;
    }
}
