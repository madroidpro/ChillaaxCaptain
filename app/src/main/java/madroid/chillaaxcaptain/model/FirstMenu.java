
package madroid.chillaaxcaptain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FirstMenu {

    @SerializedName("Starters")
    @Expose
    private String starters;
    @SerializedName("MainCourse")
    @Expose
    private String mainCourse;
    @SerializedName("Desserts")
    @Expose
    private String desserts;
    @SerializedName("Beverages")
    @Expose
    private String beverages;
    @SerializedName("Special Dish")
    @Expose
    private String specialDish;

    /**
     * 
     * @return
     *     The starters
     */
    public String getStarters() {
        return starters;
    }

    /**
     * 
     * @param starters
     *     The Starters
     */
    public void setStarters(String starters) {
        this.starters = starters;
    }

    /**
     * 
     * @return
     *     The mainCourse
     */
    public String getMainCourse() {
        return mainCourse;
    }

    /**
     * 
     * @param mainCourse
     *     The MainCourse
     */
    public void setMainCourse(String mainCourse) {
        this.mainCourse = mainCourse;
    }

    /**
     * 
     * @return
     *     The desserts
     */
    public String getDesserts() {
        return desserts;
    }

    /**
     * 
     * @param desserts
     *     The Desserts
     */
    public void setDesserts(String desserts) {
        this.desserts = desserts;
    }

    /**
     * 
     * @return
     *     The beverages
     */
    public String getBeverages() {
        return beverages;
    }

    /**
     * 
     * @param beverages
     *     The Beverages
     */
    public void setBeverages(String beverages) {
        this.beverages = beverages;
    }

    /**
     * 
     * @return
     *     The specialDish
     */
    public String getSpecialDish() {
        return specialDish;
    }

    /**
     * 
     * @param specialDish
     *     The Special Dish
     */
    public void setSpecialDish(String specialDish) {
        this.specialDish = specialDish;
    }

}
