package madroid.chillaaxcaptain.rest;


import java.util.List;

import madroid.chillaaxcaptain.model.Brand;
import madroid.chillaaxcaptain.model.FirstMenu;
import madroid.chillaaxcaptain.model.GetItem;
import madroid.chillaaxcaptain.model.OpenTables;
import madroid.chillaaxcaptain.model.OrderDetails;
import madroid.chillaaxcaptain.model.PlaceOrder;
import madroid.chillaaxcaptain.model.RestaurantInformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by madroid on 25-07-2016.
 */
public interface ApiInterface {

    @FormUrlEncoded
    @POST("decodeQr")
    Call<RestaurantInformation>decodeQr(@Field("code") String code);

    //Captain app
    @FormUrlEncoded
    @POST("stw_getTableInfo")
    Call<OpenTables>getTables(@Field("r_id") String restaurant_id,@Field("t_id") String table_id);

    @FormUrlEncoded
    @POST("stw_login")
    Call<OpenTables>login(@Field("username")String username,@Field("password")String password);

    @FormUrlEncoded
    @POST("getItems")
    Call<List<GetItem>> getItems(@Field("t_id") String t_id, @Field("type") String type);

    @FormUrlEncoded
    @POST("getCategories")
    Call<FirstMenu>getCategories(@Field("rest_id") String rest_id);

    @FormUrlEncoded
    @POST("stw_getTableById")
    Call<OrderDetails>stw_getTableById(@Field("t_id") String t_id);

    @FormUrlEncoded
    @POST("stw_placeOrder")
    Call<PlaceOrder>placeOrder(@Field("tab_id") String tab_id, @Field("original_bill") String base_bill, @Field("paid_bill") String total_bill, @Field("service_tax") String service_tax, @Field("service_charge") String service_charge, @Field("vat") String vat, @Field("krishi") String krishi, @Field("swatchbharath") String swatchbharath, @Field("items") String items, @Field("quantity") String quantity, @Field("price") String price, @Field("comments") String comments);

    @FormUrlEncoded
    @POST("raiseRequest")
    Call<ResponseBody>raiseRequest(@Field("rid") String restaurantId, @Field("tid") String tableId, @Field("type") String requestType, @Field("cacr") String paymentType);

    @FormUrlEncoded
    @POST("userFeedbacks")
    Call<ResponseBody>userFeedbacks(@Field("order_id") String order_id, @Field("name") String name, @Field("mobile") String mobile, @Field("review") String review, @Field("review_text") String review_text, @Field("dob") String dob, @Field("npacks") String npacks);
}

