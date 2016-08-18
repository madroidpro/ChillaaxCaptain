
package madroid.chillaaxcaptain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantOrderItem {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("restaurant_order_id")
    @Expose
    private String restaurantOrderId;
    @SerializedName("restaurant_menu_item_id")
    @Expose
    private String restaurantMenuItemId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("totalprice")
    @Expose
    private String totalprice;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("RestaurantOrder")
    @Expose
    private RestaurantOrder__ restaurantOrder;
    @SerializedName("RestaurantMenuItem")
    @Expose
    private RestaurantMenuItem restaurantMenuItem;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The restaurantId
     */
    public String getRestaurantId() {
        return restaurantId;
    }

    /**
     * 
     * @param restaurantId
     *     The restaurant_id
     */
    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    /**
     * 
     * @return
     *     The restaurantOrderId
     */
    public String getRestaurantOrderId() {
        return restaurantOrderId;
    }

    /**
     * 
     * @param restaurantOrderId
     *     The restaurant_order_id
     */
    public void setRestaurantOrderId(String restaurantOrderId) {
        this.restaurantOrderId = restaurantOrderId;
    }

    /**
     * 
     * @return
     *     The restaurantMenuItemId
     */
    public String getRestaurantMenuItemId() {
        return restaurantMenuItemId;
    }

    /**
     * 
     * @param restaurantMenuItemId
     *     The restaurant_menu_item_id
     */
    public void setRestaurantMenuItemId(String restaurantMenuItemId) {
        this.restaurantMenuItemId = restaurantMenuItemId;
    }

    /**
     * 
     * @return
     *     The quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * 
     * @param quantity
     *     The quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * 
     * @return
     *     The totalprice
     */
    public String getTotalprice() {
        return totalprice;
    }

    /**
     * 
     * @param totalprice
     *     The totalprice
     */
    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    /**
     * 
     * @return
     *     The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * 
     * @param comment
     *     The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 
     * @return
     *     The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * 
     * @param created
     *     The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * 
     * @return
     *     The modified
     */
    public String getModified() {
        return modified;
    }

    /**
     * 
     * @param modified
     *     The modified
     */
    public void setModified(String modified) {
        this.modified = modified;
    }

    /**
     * 
     * @return
     *     The restaurantOrder
     */
    public RestaurantOrder__ getRestaurantOrder() {
        return restaurantOrder;
    }

    /**
     * 
     * @param restaurantOrder
     *     The RestaurantOrder
     */
    public void setRestaurantOrder(RestaurantOrder__ restaurantOrder) {
        this.restaurantOrder = restaurantOrder;
    }

    /**
     * 
     * @return
     *     The restaurantMenuItem
     */
    public RestaurantMenuItem getRestaurantMenuItem() {
        return restaurantMenuItem;
    }

    /**
     * 
     * @param restaurantMenuItem
     *     The RestaurantMenuItem
     */
    public void setRestaurantMenuItem(RestaurantMenuItem restaurantMenuItem) {
        this.restaurantMenuItem = restaurantMenuItem;
    }

}
