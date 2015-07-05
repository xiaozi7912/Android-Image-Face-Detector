package org.larry.imagefacedetector.activities;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;

import org.larry.imagefacedetector.R;
import org.larry.imagefacedetector.adapters.FolderAdapter;
import org.larry.imagefacedetector.adapters.ImageAdapter;
import org.larry.imagefacedetector.models.FileModel;
import org.larry.imagefacedetector.models.FolderModel;
import org.larry.imagefacedetector.models.ImageModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Larry on 2015/7/5.
 */
public class ImageListActivity extends BaseActivity {
    private ArrayList<FolderModel> mFolderList = null;
    private ArrayList<ImageModel> mImageList = null;

    private Spinner mFolderListView = null;
    private GridView mImageGridView = null;

    private final String FILE_ROOT_PATH = Environment.getExternalStorageDirectory().getPath();

    private String mCurrentPath = FILE_ROOT_PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagelist);

        initView();
        getFolderList();
    }

    private void initView() {
        Log.i(LOG_TAG, "initView");
        mFolderListView = (Spinner) findViewById(R.id.imagelist_spinner_folder);
        mImageGridView = (GridView) findViewById(R.id.imagelist_grid);

        mFolderListView.setOnItemSelectedListener(onItemSelectedListener);
        mImageGridView.setOnItemSelectedListener(onItemSelectedListener);
        mImageGridView.setOnItemClickListener(onItemClickListener);
    }

    private void getFolderList() {
        Log.i(LOG_TAG, "getFolderList");
        mFolderList = new ArrayList<FolderModel>();
        File currentFolder = new File(mCurrentPath);
        File[] files = currentFolder.listFiles();

        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                return (lhs.getName().compareToIgnoreCase(rhs.getName()));
            }
        });

        for (File f : files) {
            if (f.isDirectory() && !f.isHidden()) {
                FolderModel folderModel = new FolderModel();
                folderModel.fileName = f.getName();
                folderModel.filePath = f.getPath();
                folderModel.fileSize = f.getTotalSpace();

                Log.v(LOG_TAG, "getName : " + f.getName());
                Log.v(LOG_TAG, "getPath : " + f.getPath());
                Log.v(LOG_TAG, "getTotalSpace : " + f.getTotalSpace());
                Log.v(LOG_TAG, "getFreeSpace : " + f.getFreeSpace());
                Log.v(LOG_TAG, "getUsableSpace : " + f.getUsableSpace());
                Log.i(LOG_TAG, "-------------------------");
                mFolderList.add(folderModel);
            }
        }
        setFolderAdapter();
    }

    private void setFolderAdapter() {
        FolderAdapter adapter = new FolderAdapter(mActivity, mFolderList);
        mFolderListView.setAdapter(adapter);
    }

    private void onFolderSelected(int position) {
        FolderModel selectedFolder = mFolderList.get(position);
        mCurrentPath = selectedFolder.filePath;
    }

    private void getImageList() {
        Log.i(LOG_TAG, "getImageList");
        mImageList = new ArrayList<ImageModel>();
        File currentFolder = new File(mCurrentPath);
        File[] files = currentFolder.listFiles();

        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                return (lhs.getName().compareToIgnoreCase(rhs.getName()));
            }
        });

        for (File f : files) {
            if (f.isFile() && !f.isHidden()) {
                ImageModel imageModel = new ImageModel();
                imageModel.fileName = f.getName();
                imageModel.filePath = f.getPath();
                imageModel.fileSize = f.getTotalSpace();

                Log.v(LOG_TAG, "getName : " + f.getName());
                Log.v(LOG_TAG, "getPath : " + f.getPath());
                Log.v(LOG_TAG, "getTotalSpace : " + f.getTotalSpace());
                Log.v(LOG_TAG, "getFreeSpace : " + f.getFreeSpace());
                Log.v(LOG_TAG, "getUsableSpace : " + f.getUsableSpace());
                Log.i(LOG_TAG, "-------------------------");
                mImageList.add(imageModel);
            }
        }
        setImageAdapter();
    }

    private void setImageAdapter() {
        ImageAdapter adapter = new ImageAdapter(mActivity, mImageList);
        mImageGridView.setAdapter(adapter);
    }

    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.i(LOG_TAG, "onItemSelected");
            switch (parent.getId()) {
                case R.id.imagelist_spinner_folder:
                    onFolderSelected(position);
                    getImageList();
                    break;
                case R.id.imagelist_grid:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(LOG_TAG, "onItemClick");
        }
    };
}
