
package madroid.chillaaxcaptain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantMenuItemGroup {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("restaurant_menu_list_id")
    @Expose
    private String restaurantMenuListId;
    @SerializedName("avail_status")
    @Expose
    private String availStatus;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("group_img")
    @Expose
    private String groupImg;

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
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
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
     *     The parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 
     * @param parentId
     *     The parent_id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
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
     *     The discount
     */
    public String getDiscount() {
        return discount;
    }

    /**
     * 
     * @param discount
     *     The discount
     */
    public void setDiscount(String discount) {
        this.discount = discount;
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
     *     The groupImg
     */
    public String getGroupImg() {
        return groupImg;
    }

    /**
     * 
     * @param groupImg
     *     The group_img
     */
    public void setGroupImg(String groupImg) {
        this.groupImg = groupImg;
    }

}
