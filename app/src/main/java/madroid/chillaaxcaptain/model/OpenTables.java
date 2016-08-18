
package madroid.chillaaxcaptain.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpenTables {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("tables")
    @Expose
    private List<Table> tables = new ArrayList<Table>();
    @SerializedName("t_status")
    @Expose
    private String tStatus;
    @SerializedName("Restaurant")
    @Expose
    private Restaurant restaurant;
    @SerializedName("brand")
    @Expose
    private Brand brand;

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
     *     The tables
     */
    public List<Table> getTables() {
        return tables;
    }

    /**
     * 
     * @param tables
     *     The tables
     */
    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    /**
     * 
     * @return
     *     The tStatus
     */
    public String getTStatus() {
        return tStatus;
    }

    /**
     *
     * @return
     *     The restaurant
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }

    /**
     *
     * @param restaurant
     *     The Restaurant
     */
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }


    /**
     * 
     * @param tStatus
     *     The t_status
     */
    public void setTStatus(String tStatus) {
        this.tStatus = tStatus;
    }

    /**
     *
     * @return
     *     The brand
     */
    public Brand getBrand() {
        return brand;
    }

    /**
     *
     * @param brand
     *     The brand
     */
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

}
