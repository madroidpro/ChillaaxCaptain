
package madroid.chillaaxcaptain.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantInformation {

    @SerializedName("RestaurantTable")
    @Expose
    private RestaurantTable restaurantTable;
    @SerializedName("Restaurant")
    @Expose
    private Restaurant restaurant;
    @SerializedName("Brand")
    @Expose
    private Brand brand;

    /**
     * 
     * @return
     *     The restaurantTable
     */

    public RestaurantTable getRestaurantTable() {
        return restaurantTable;
    }

    /**
     * 
     * @param restaurantTable
     *     The RestaurantTable
     */
    public void setRestaurantTable(RestaurantTable restaurantTable) {
        this.restaurantTable = restaurantTable;
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
     * @return
     *     The brand
     */
    public Brand getBrand() {
        return brand;
    }

    /**
     * 
     * @param brand
     *     The Brand
     */
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

}
