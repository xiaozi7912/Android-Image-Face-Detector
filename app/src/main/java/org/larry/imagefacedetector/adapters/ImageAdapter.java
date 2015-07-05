package org.larry.imagefacedetector.adapters;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.larry.imagefacedetector.R;
import org.larry.imagefacedetector.models.ImageModel;

import java.util.ArrayList;

/**
 * Created by Larry on 2015/7/5.
 */
public class ImageAdapter extends MyBaseAdapter {
    private ArrayList<ImageModel> mDataList = null;

    public ImageAdapter(Activity activity, ArrayList<ImageModel> dataList) {
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
            convertView = inflater.inflate(R.layout.item_image, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.item_image_image);
            holder.fileName = (TextView) convertView.findViewById(R.id.item_image_name);
            convertView.setTag(holder);
        }

        ImageModel selectedImage = mDataList.get(position);
        holder.image.setImageURI(Uri.parse(selectedImage.filePath));
        holder.fileName.setText(selectedImage.fileName);
        return convertView;
    }

    class ViewHolder {
        public ImageView image = null;
        public TextView fileName = null;
    }
}
