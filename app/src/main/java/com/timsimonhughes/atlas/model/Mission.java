package com.timsimonhughes.atlas.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Mission implements Parcelable {
    private String missionUrl;
    private String missionTitle;

    public Mission(String missionUrl, String missionTitle) {
        this.missionUrl = missionUrl;
        this.missionTitle = missionTitle;
    }

    private Mission(Parcel in) {
        missionUrl = in.readString();
        missionTitle = in.readString();
    }

    public static final Creator<Mission> CREATOR = new Creator<Mission>() {
        @Override
        public Mission createFromParcel(Parcel in) {
            return new Mission(in);
        }

        @Override
        public Mission[] newArray(int size) {
            return new Mission[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(missionUrl);
        dest.writeString(missionTitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getMissionUrl() {
        return missionUrl;
    }

    public void setMissionUrl(String missionUrl) {
        this.missionUrl = missionUrl;
    }

    public String getMissionTitle() {
        return missionTitle;
    }

    public void setMissionTitle(String missionTitle) {
        this.missionTitle = missionTitle;
    }
}
