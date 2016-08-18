
package madroid.chillaaxcaptain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Restaurants {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("qr_type")
    @Expose
    private String qrType;
    @SerializedName("mbl")
    @Expose
    private Integer mbl;
    @SerializedName("data")
    @Expose
    private RestaurantInformation data;

    /**
     * 
     * @return
     *     The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The qrType
     */
    public String getQrType() {
        return qrType;
    }

    /**
     * 
     * @param qrType
     *     The qr_type
     */
    public void setQrType(String qrType) {
        this.qrType = qrType;
    }

    /**
     * 
     * @return
     *     The mbl
     */
    public Integer getMbl() {
        return mbl;
    }

    /**
     * 
     * @param mbl
     *     The mbl
     */
    public void setMbl(Integer mbl) {
        this.mbl = mbl;
    }

    /**
     * 
     * @return
     *     The data
     */
    public RestaurantInformation getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(RestaurantInformation data) {
        this.data = data;
    }

}
