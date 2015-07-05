package org.larry.imagefacedetector.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.larry.imagefacedetector.R;

import java.io.File;

/**
 * Created by Larry on 2015/7/5.
 */
public class MainActivity extends BaseActivity {
    private FaceDetector mFaceDetector = null;
    private Handler mHandler = new Handler();

    private ImageView mImagePicture = null;
    private ImageView mImageDetected = null;
    private Button mButtonFaceDetect = null;

    private final int REQUEST_CODE_SELECT_IMAGE = 1;
    private final int MAX_FACES = 10;

    private Uri mSelectedImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(LOG_TAG, "onActivityResult");

        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectedImageUri = data.getData();
                Log.v(LOG_TAG, "toString : " + data.toString());
                Log.v(LOG_TAG, "getPath : " + mSelectedImageUri.getPath());
                Log.v(LOG_TAG, "getAuthority : " + mSelectedImageUri.getAuthority());
                Log.v(LOG_TAG, "getRealPathFromUri : " + getRealPathFromUri(mSelectedImageUri));
                mImagePicture.setImageURI(mSelectedImageUri);
            }
        }
        Log.i(LOG_TAG, "-------------------------");
    }

    private void initView() {
        Log.i(LOG_TAG, "initView");
        mImagePicture = (ImageView) findViewById(R.id.main_image_picture);
        mImageDetected = (ImageView) findViewById(R.id.main_image_detected);
        mButtonFaceDetect = (Button) findViewById(R.id.main_button_face_detect);

        mImagePicture.setOnClickListener(onClickListener);
        mButtonFaceDetect.setOnClickListener(onClickListener);
    }

    private void detectFace() {
        Log.i(LOG_TAG, "detectFace");
        if (mSelectedImageUri != null) {
            FaceDetector.Face[] faces = new FaceDetector.Face[MAX_FACES];
            File imageFile = new File(mSelectedImageUri.getPath());
            Log.v(LOG_TAG, "getPath : " + imageFile.getPath());
            Log.v(LOG_TAG, "getAbsolutePath : " + imageFile.getAbsolutePath());
            Log.v(LOG_TAG, "getRealPathFromUri : " + getRealPathFromUri(mSelectedImageUri));
            Log.i(LOG_TAG, "-------------------------");
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap imageBitmap = BitmapFactory.decodeFile(getRealPathFromUri(mSelectedImageUri), bitmapOptions);
            final Bitmap detectedBitmap = imageBitmap.copy(Bitmap.Config.RGB_565, true);
            int faceCount = 0;

            try {
                mFaceDetector = new FaceDetector(imageBitmap.getWidth(), imageBitmap.getHeight(), MAX_FACES);
                faceCount = mFaceDetector.findFaces(imageBitmap, faces);
                Log.v(LOG_TAG, "getWidth : " + imageBitmap.getWidth());
                Log.v(LOG_TAG, "getHeight : " + imageBitmap.getHeight());
                Log.v(LOG_TAG, "faceCount : " + faceCount);
                Log.v(LOG_TAG, "faces.length : " + faces.length);
                Log.i(LOG_TAG, "-------------------------");

                if (faces.length != 0) {
                    Canvas canvas = new Canvas(detectedBitmap);
                    Paint pointPaint = new Paint();
                    pointPaint.setColor(Color.RED);
                    pointPaint.setStyle(Paint.Style.FILL);
                    pointPaint.setAntiAlias(true);
                    pointPaint.setStrokeWidth(20f);

                    for (int i = 0; i < faces.length; i++) {
                        FaceDetector.Face face = faces[i];
                        Log.v(LOG_TAG, "i : " + i);
                        Log.v(LOG_TAG, "face : " + face);
                        PointF midPoint = new PointF();

                        face.getMidPoint(midPoint);
                        canvas.drawPoint(midPoint.x, midPoint.y, pointPaint);

                        Log.v(LOG_TAG, "midPoint.x : " + midPoint.x);
                        Log.v(LOG_TAG, "midPoint.y : " + midPoint.y);
                        Log.v(LOG_TAG, "confidence : " + face.confidence());
                        Log.v(LOG_TAG, "eyesDistance : " + face.eyesDistance());
                        Log.i(LOG_TAG, "-------------------------");
                    }
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            } finally {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mImageDetected.setImageBitmap(detectedBitmap);
                    }
                });
            }
        }
    }

    private void selectImageFromDevice() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    private String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_image_picture:
                    selectImageFromDevice();
                    break;
                case R.id.main_button_face_detect:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            detectFace();
                        }
                    }).start();
                    break;
            }

        }
    };
}
