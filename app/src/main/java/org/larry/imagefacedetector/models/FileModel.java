package org.larry.imagefacedetector.models;

/**
 * Created by Larry on 2015/7/5.
 */
public class FileModel {
    public static int FILE_TYPE_FOLDER = 1;
    public static int FILE_TYPE_IMAGE = FILE_TYPE_FOLDER + 1;

    public String fileName = null;
    public String filePath = null;
    public int fileType = 0;
    public long fileSize = 0;
    public String createTime = null;
}
