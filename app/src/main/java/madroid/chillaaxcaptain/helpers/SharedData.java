package madroid.chillaaxcaptain.helpers;

/**
 * Created by madroid on 27-07-2016.
 */
public class SharedData {
    private static SharedData singletonObject;
    public static final String Flaglists ="";
    public static final String BaseUrlImg="http://www.chillaax.com/chillax-services/img/";
    public static final String username="1947BSK";
    public static final String password="BSKADMIN";
    //TODO change  to live url when releasing
    public static final String BaseUrl="http://www.chillaax.com/chillax-services/Services/";
    //public static final String BaseUrl="http://www.chillaax.in/Services/";
    public static synchronized SharedData getSingletonObject(){
        if(singletonObject == null){
            return new SharedData();
        }else{
            return singletonObject;
        }
    }

    private static String RestaurantId;
    private static String RestaurantName;
    private static String RestaurantAddress;
    private static String RestaurantLocation;
    private static String RestaurantBrandId;
    private static String RestaurantTableId;
    private static String RestaurantTableDisplayName;
    private static String RestaurantTableStatus;
    private static String BrandName;
    private static String BrandVat;
    private static String BrandServiceTax;

    public static String getBrandServiceCharge() {
        return BrandServiceCharge;
    }

    public static void setBrandServiceCharge(String brandServiceCharge) {
        BrandServiceCharge = brandServiceCharge;
    }

    private static String BrandServiceCharge;
    private static String Swachbarath;
    private static String Krishikalyan;
    private static String Vat2;
    private static String BrandType;

    public String getRestaurantId() {
        return RestaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        RestaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return RestaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        RestaurantAddress = restaurantAddress;
    }

    public static String getRestaurantTableDisplayName() {
        return RestaurantTableDisplayName;
    }

    public static void setRestaurantTableDisplayName(String restaurantTableDisplayName) {
        RestaurantTableDisplayName = restaurantTableDisplayName;
    }
    public String getRestaurantLocation() {
        return RestaurantLocation;
    }

    public void setRestaurantLocation(String restaurantLocation) {
        RestaurantLocation = restaurantLocation;
    }

    public String getRestaurantBrandId() {
        return RestaurantBrandId;
    }

    public void setRestaurantBrandId(String restaurantBrandId) {
        RestaurantBrandId = restaurantBrandId;
    }

    public String getRestaurantTableId() {
        return RestaurantTableId;
    }

    public void setRestaurantTableId(String restaurantTableId) {
        RestaurantTableId = restaurantTableId;
    }

    public String getRestaurantTableStatus() {
        return RestaurantTableStatus;
    }

    public void setRestaurantTableStatus(String restaurantTableStatus) {
        RestaurantTableStatus = restaurantTableStatus;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getBrandVat() {
        return BrandVat;
    }

    public void setBrandVat(String brandVat) {
        BrandVat = brandVat;
    }

    public String getBrandServiceTax() {
        return BrandServiceTax;
    }

    public void setBrandServiceTax(String brandServiceTax) {
        BrandServiceTax = brandServiceTax;
    }

    public String getSwachbarath() {
        return Swachbarath;
    }

    public void setSwachbarath(String swachbarath) {
        this.Swachbarath = swachbarath;
    }

    public String getKrishikalyan() {
        return Krishikalyan;
    }

    public void setKrishikalyan(String krishikalyan) {
        this.Krishikalyan = krishikalyan;
    }

    public String getVat2() {
        return Vat2;
    }

    public void setVat2(String vat2) {
        this.Vat2 = vat2;
    }

    public String getBrandType() {
        return BrandType;
    }

    public void setBrandType(String brandType) {
        BrandType = brandType;
    }
}
