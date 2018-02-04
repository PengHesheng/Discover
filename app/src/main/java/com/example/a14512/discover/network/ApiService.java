package com.example.a14512.discover.network;


import com.example.a14512.discover.modules.main.mode.entity.weather.WeatherData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author 14512 on 2017/8/15.
 */

public interface ApiService {

    String BASE_URL = "http://119.28.23.46/xinhuo/";
    String WEATHER = "https://free-api.heweather.com/s6/weather/";



    //---------------------user---------------------------------------------

    /**
     * 获取七牛云token
     * @return Result<String>
     * */
    @POST("getToken.php")
    Observable<Result<String>> getToken();

    /**
     * @param location
     * @param key
     * @return
     */
    @GET("now?")
    Observable<WeatherData> getWeather(@Query("location") String location,
                                       @Query("key") String key);

}
