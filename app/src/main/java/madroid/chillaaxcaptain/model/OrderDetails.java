
package madroid.chillaaxcaptain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderDetails {

    @SerializedName("RestaurantTable")
    @Expose
    private RestaurantTable restaurantTable;
    @SerializedName("RestaurantOrder")
    @Expose
    private List<RestaurantOrder> restaurantOrder = new ArrayList<RestaurantOrder>();

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
     *     The restaurantOrder
     */
    public List<RestaurantOrder> getRestaurantOrder() {
        return restaurantOrder;
    }

    /**
     * 
     * @param restaurantOrder
     *     The RestaurantOrder
     */
    public void setRestaurantOrder(List<RestaurantOrder> restaurantOrder) {
        this.restaurantOrder = restaurantOrder;
    }

}
