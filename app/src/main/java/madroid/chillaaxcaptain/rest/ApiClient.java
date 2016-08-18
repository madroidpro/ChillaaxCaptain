package madroid.chillaaxcaptain.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import madroid.chillaaxcaptain.adapter.ItemTypeAdapterFactory;
import madroid.chillaaxcaptain.helpers.SharedData;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by madroid on 25-07-2016.
 */
public class ApiClient {
   public static SharedData sd = SharedData.getSingletonObject();
    private static Retrofit retrofit=null;

    public static Retrofit getClient(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory()) // This is the important line ;)
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        if(retrofit == null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(sd.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
