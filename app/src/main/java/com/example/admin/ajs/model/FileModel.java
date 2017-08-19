package com.example.admin.ajs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABC on 8/17/2017.
 */

public class FileModel implements Serializable {
    @SerializedName("DataId")
    @Expose
    private int dataId = 0;

    @SerializedName("FileId")
    @Expose
    private int FileId = 0;

    @SerializedName("FileTitle")
    @Expose
    private String FileTitle = "";

    @SerializedName("FileDate")
    @Expose
    private String FileDate = "";

    @SerializedName("FilePath")
    @Expose
    private String FilePath = "";

    @SerializedName("CreatedOn")
    @Expose
    private String CreatedOn = "";

    @SerializedName("EndDate")
    @Expose
    private String EndDate = "";


    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public int getFileId() {
        return FileId;
    }

    public void setFileId(int fileId) {
        FileId = fileId;
    }

    public String getFileTitle() {
        return FileTitle;
    }

    public void setFileTitle(String fileTitle) {
        FileTitle = fileTitle;
    }

    public String getFileDate() {
        return FileDate;
    }

    public void setFileDate(String fileDate) {
        FileDate = fileDate;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getEndDate() {
        return EndDate;
    }
    private int position = 0;
    @SerializedName("FilePath")


    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
