package com.timsimonhughes.atlas.model;

import android.os.Parcel;
import android.os.Parcelable;

public class POTD implements Parcelable {

    private String date;
    private String explanation;
    private String hdurl;
    private String media_type;
    private String service_version;
    private String title;
    private String url;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getHdurl() {
        return hdurl;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getService_version() {
        return service_version;
    }

    public void setService_version(String service_version) {
        this.service_version = service_version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public POTD(Parcel in) {
        date = in.readString();
        explanation = in.readString();
        hdurl = in.readString();
        media_type = in.readString();
        service_version = in.readString();
        title = in.readString();
        url = in.readString();
    }

    public static final Creator<POTD> CREATOR = new Creator<POTD>() {
        @Override
        public POTD createFromParcel(Parcel in) {
            return new POTD(in);
        }

        @Override
        public POTD[] newArray(int size) {
            return new POTD[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(explanation);
        dest.writeString(hdurl);
        dest.writeString(media_type);
        dest.writeString(service_version);
        dest.writeString(title);
        dest.writeString(url);
    }
}
