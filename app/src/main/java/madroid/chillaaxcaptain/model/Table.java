
package madroid.chillaaxcaptain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Table {

    @SerializedName("RestaurantTable")
    @Expose
    private RestaurantTable restaurantTable;

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

}
