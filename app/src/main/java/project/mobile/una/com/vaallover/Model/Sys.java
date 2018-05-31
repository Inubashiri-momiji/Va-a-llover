
package project.mobile.una.com.vaallover.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class Sys extends RealmObject {

    @SerializedName("pod")
    @Expose
    private String pod;

    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("sunrise")
    @Expose
    private Integer sunrise;
    @SerializedName("sunset")
    @Expose
    private Integer sunset;
    /**
     * No args constructor for use in serialization
     * 
     */
    public Sys() {
    }

    /**
     * 
     * @param pod
     */
    public Sys(String pod) {
        super();
        this.pod = pod;
    }
    /**
     *
     * @param message
     * @param sunset
     * @param sunrise
     * @param country
     */
    public Sys(Double message, String country, Integer sunrise, Integer sunset) {
        super();
        this.message = message;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }
    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

}
