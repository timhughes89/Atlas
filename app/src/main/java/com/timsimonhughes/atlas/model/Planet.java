package com.timsimonhughes.atlas.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Planet implements Parcelable {
    private String title;
    private String image_url;
    private String overview;
    private String orbital_period;
    private String distance_sol;
    private String moons;
    private float orbital_speed;
    private String planet_type;

    public Planet(String title, String image_url, String overview, String orbital_period, String distance_sol, String moons, float orbital_speed, String planet_type) {
        this.title = title;
        this.image_url = image_url;
        this.overview = overview;
        this.orbital_period = orbital_period;
        this.distance_sol = distance_sol;
        this.moons = moons;
        this.orbital_speed = orbital_speed;
        this.planet_type = planet_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOrbital_period() {
        return orbital_period;
    }

    public void setOrbital_period(String orbital_period) {
        this.orbital_period = orbital_period;
    }

    public String getDistance_sol() {
        return distance_sol;
    }

    public void setDistance_sol(String distance_sol) {
        this.distance_sol = distance_sol;
    }

    public String getMoons() {
        return moons;
    }

    public void setMoons(String moons) {
        this.moons = moons;
    }

    public float getOrbital_speed() {
        return orbital_speed;
    }

    public void setOrbital_speed(float orbital_speed) {
        this.orbital_speed = orbital_speed;
    }

    public String getPlanet_type() {
        return planet_type;
    }

    public void setPlanet_type(String planet_type) {
        this.planet_type = planet_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Planet(Parcel in) {
        title = in.readString();
        image_url = in.readString();
        overview = in.readString();
        orbital_period = in.readString();
        distance_sol = in.readString();
        moons = in.readString();
        orbital_speed = in.readFloat();
        planet_type = in.readString();
    }

    public static final Creator<Planet> CREATOR = new Creator<Planet>() {
        @Override
        public Planet createFromParcel(Parcel in) {
            return new Planet(in);
        }

        @Override
        public Planet[] newArray(int size) {
            return new Planet[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image_url);
        dest.writeString(overview);
        dest.writeString(orbital_period);
        dest.writeString(distance_sol);
        dest.writeString(moons);
        dest.writeFloat(orbital_speed);
        dest.writeString(planet_type);
    }
}

