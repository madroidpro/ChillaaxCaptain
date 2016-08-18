
package madroid.chillaaxcaptain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantMenuItem {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("restaurant_menu_item_group_id")
    @Expose
    private String restaurantMenuItemGroupId;
    @SerializedName("restaurant_menu_list_id")
    @Expose
    private String restaurantMenuListId;
    @SerializedName("item_price")
    @Expose
    private String itemPrice;
    @SerializedName("price_type")
    @Expose
    private String priceType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("veg_non")
    @Expose
    private String vegNon;
    @SerializedName("item_img_big")
    @Expose
    private String itemImgBig;
    @SerializedName("item_img_small")
    @Expose
    private String itemImgSmall;
    @SerializedName("preparation_time")
    @Expose
    private String preparationTime;
    @SerializedName("avail_status")
    @Expose
    private String availStatus;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;

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
     *     The itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * @param itemName
     *     The item_name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
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
     *     The restaurantMenuItemGroupId
     */
    public String getRestaurantMenuItemGroupId() {
        return restaurantMenuItemGroupId;
    }

    /**
     * 
     * @param restaurantMenuItemGroupId
     *     The restaurant_menu_item_group_id
     */
    public void setRestaurantMenuItemGroupId(String restaurantMenuItemGroupId) {
        this.restaurantMenuItemGroupId = restaurantMenuItemGroupId;
    }

    /**
     * 
     * @return
     *     The restaurantMenuListId
     */
    public String getRestaurantMenuListId() {
        return restaurantMenuListId;
    }

    /**
     * 
     * @param restaurantMenuListId
     *     The restaurant_menu_list_id
     */
    public void setRestaurantMenuListId(String restaurantMenuListId) {
        this.restaurantMenuListId = restaurantMenuListId;
    }

    /**
     * 
     * @return
     *     The itemPrice
     */
    public String getItemPrice() {
        return itemPrice;
    }

    /**
     * 
     * @param itemPrice
     *     The item_price
     */
    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     * 
     * @return
     *     The priceType
     */
    public String getPriceType() {
        return priceType;
    }

    /**
     * 
     * @param priceType
     *     The price_type
     */
    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The vegNon
     */
    public String getVegNon() {
        return vegNon;
    }

    /**
     * 
     * @param vegNon
     *     The veg_non
     */
    public void setVegNon(String vegNon) {
        this.vegNon = vegNon;
    }

    /**
     * 
     * @return
     *     The itemImgBig
     */
    public String getItemImgBig() {
        return itemImgBig;
    }

    /**
     * 
     * @param itemImgBig
     *     The item_img_big
     */
    public void setItemImgBig(String itemImgBig) {
        this.itemImgBig = itemImgBig;
    }

    /**
     * 
     * @return
     *     The itemImgSmall
     */
    public String getItemImgSmall() {
        return itemImgSmall;
    }

    /**
     * 
     * @param itemImgSmall
     *     The item_img_small
     */
    public void setItemImgSmall(String itemImgSmall) {
        this.itemImgSmall = itemImgSmall;
    }

    /**
     * 
     * @return
     *     The preparationTime
     */
    public String getPreparationTime() {
        return preparationTime;
    }

    /**
     * 
     * @param preparationTime
     *     The preparation_time
     */
    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    /**
     * 
     * @return
     *     The availStatus
     */
    public String getAvailStatus() {
        return availStatus;
    }

    /**
     * 
     * @param availStatus
     *     The avail_status
     */
    public void setAvailStatus(String availStatus) {
        this.availStatus = availStatus;
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

}
