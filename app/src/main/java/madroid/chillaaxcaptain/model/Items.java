
package madroid.chillaaxcaptain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Items {

    @SerializedName("RestaurantOrder")
    @Expose
    private RestaurantOrder_ restaurantOrder;
    @SerializedName("RestaurantOrderItem")
    @Expose
    private List<RestaurantOrderItem> restaurantOrderItem = new ArrayList<RestaurantOrderItem>();

    /**
     * 
     * @return
     *     The restaurantOrder
     */
    public RestaurantOrder_ getRestaurantOrder() {
        return restaurantOrder;
    }

    /**
     * 
     * @param restaurantOrder
     *     The RestaurantOrder
     */
    public void setRestaurantOrder(RestaurantOrder_ restaurantOrder) {
        this.restaurantOrder = restaurantOrder;
    }

    /**
     * 
     * @return
     *     The restaurantOrderItem
     */
    public List<RestaurantOrderItem> getRestaurantOrderItem() {
        return restaurantOrderItem;
    }

    /**
     * 
     * @param restaurantOrderItem
     *     The RestaurantOrderItem
     */
    public void setRestaurantOrderItem(List<RestaurantOrderItem> restaurantOrderItem) {
        this.restaurantOrderItem = restaurantOrderItem;
    }

}
