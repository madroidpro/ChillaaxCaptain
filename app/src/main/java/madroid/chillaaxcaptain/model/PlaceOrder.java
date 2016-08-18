
package madroid.chillaaxcaptain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceOrder {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("orderid")
    @Expose
    private String orderid;

    /**
     * 
     * @return
     *     The status
     */
    public int getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The orderid
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * 
     * @param orderid
     *     The orderid
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

}
