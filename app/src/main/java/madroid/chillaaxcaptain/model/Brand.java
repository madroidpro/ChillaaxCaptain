
package madroid.chillaaxcaptain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brand {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("background")
    @Expose
    private String background;
    @SerializedName("owner_id")
    @Expose
    private String ownerId;
    @SerializedName("vat")
    @Expose
    private String vat;
    @SerializedName("service_tax")
    @Expose
    private String serviceTax;
    @SerializedName("service_charge")
    @Expose
    private String serviceCharge;
    @SerializedName("swachbarath")
    @Expose
    private String swachbarath;
    @SerializedName("krishikalyan")
    @Expose
    private String krishikalyan;
    @SerializedName("vat2")
    @Expose
    private String vat2;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("created")
    @Expose
    private String created;

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
     *     The logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 
     * @param logo
     *     The logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 
     * @return
     *     The background
     */
    public String getBackground() {
        return background;
    }

    /**
     * 
     * @param background
     *     The background
     */
    public void setBackground(String background) {
        this.background = background;
    }

    /**
     * 
     * @return
     *     The ownerId
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * 
     * @param ownerId
     *     The owner_id
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * 
     * @return
     *     The vat
     */
    public String getVat() {
        return vat;
    }

    /**
     * 
     * @param vat
     *     The vat
     */
    public void setVat(String vat) {
        this.vat = vat;
    }

    /**
     * 
     * @return
     *     The serviceTax
     */
    public String getServiceTax() {
        return serviceTax;
    }

    /**
     * 
     * @param serviceTax
     *     The service_tax
     */
    public void setServiceTax(String serviceTax) {
        this.serviceTax = serviceTax;
    }

    /**
     * 
     * @return
     *     The serviceCharge
     */
    public String getServiceCharge() {
        return serviceCharge;
    }

    /**
     * 
     * @param serviceCharge
     *     The service_charge
     */
    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    /**
     * 
     * @return
     *     The swachbarath
     */
    public String getSwachbarath() {
        return swachbarath;
    }

    /**
     * 
     * @param swachbarath
     *     The swachbarath
     */
    public void setSwachbarath(String swachbarath) {
        this.swachbarath = swachbarath;
    }

    /**
     * 
     * @return
     *     The krishikalyan
     */
    public String getKrishikalyan() {
        return krishikalyan;
    }

    /**
     * 
     * @param krishikalyan
     *     The krishikalyan
     */
    public void setKrishikalyan(String krishikalyan) {
        this.krishikalyan = krishikalyan;
    }

    /**
     * 
     * @return
     *     The vat2
     */
    public String getVat2() {
        return vat2;
    }

    /**
     * 
     * @param vat2
     *     The vat2
     */
    public void setVat2(String vat2) {
        this.vat2 = vat2;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
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

}
