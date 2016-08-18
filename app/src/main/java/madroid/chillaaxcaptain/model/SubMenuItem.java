package madroid.chillaaxcaptain.model;

/**
 * Created by madroid on 29-07-2016.
 */
public class SubMenuItem {

    public SubMenuItem(){}

    public String getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemPrice() {
        return menuItemPrice;
    }

    public void setMenuItemPrice(String menuItemPrice) {
        this.menuItemPrice = menuItemPrice;
    }

    public String getMenuItemImg() {
        return menuItemImg;
    }

    public void setMenuItemImg(String menuItemImg) {
        this.menuItemImg = menuItemImg;
    }

    public String getMenuItemDesc() {
        return menuItemDesc;
    }

    public void setMenuItemDesc(String menuItemDesc) {
        this.menuItemDesc = menuItemDesc;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    private String menuItemId,menuItemName,menuItemDesc,menuItemImg,menuItemPrice;
}
