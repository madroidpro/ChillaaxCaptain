
package madroid.chillaaxcaptain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetItem {

    @SerializedName("RestaurantMenuItemGroup")
    @Expose
    private RestaurantMenuItemGroup restaurantMenuItemGroup;
    @SerializedName("RestaurantMenuItem")
    @Expose
    private List<RestaurantMenuItem> restaurantMenuItem = new ArrayList<RestaurantMenuItem>();

    /**
     * 
     * @return
     *     The restaurantMenuItemGroup
     */
    public RestaurantMenuItemGroup getRestaurantMenuItemGroup() {
        return restaurantMenuItemGroup;
    }

    /**
     * 
     * @param restaurantMenuItemGroup
     *     The RestaurantMenuItemGroup
     */
    public void setRestaurantMenuItemGroup(RestaurantMenuItemGroup restaurantMenuItemGroup) {
        this.restaurantMenuItemGroup = restaurantMenuItemGroup;
    }

    /**
     * 
     * @return
     *     The restaurantMenuItem
     */
    public List<RestaurantMenuItem> getRestaurantMenuItem() {
        return restaurantMenuItem;
    }

    /**
     * 
     * @param restaurantMenuItem
     *     The RestaurantMenuItem
     */
    public void setRestaurantMenuItem(List<RestaurantMenuItem> restaurantMenuItem) {
        this.restaurantMenuItem = restaurantMenuItem;
    }

}
