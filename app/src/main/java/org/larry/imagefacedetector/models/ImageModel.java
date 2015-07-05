package org.larry.imagefacedetector.models;

/**
 * Created by Larry on 2015/7/5.
 */
public class ImageModel extends FileModel {
    public int width = 0;
    public int height = 0;
    public String ext = null;

    public ImageModel() {
        fileType = FILE_TYPE_IMAGE;
    }
}
