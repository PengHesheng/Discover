package com.example.a14512.discover.network;


import com.example.a14512.discover.modules.login.mode.RegisterData;
import com.example.a14512.discover.modules.main.mode.entity.UserInfo;
import com.example.a14512.discover.modules.main.mode.entity.WeatherData;
import com.example.a14512.discover.modules.main.userself.myroute.mode.entity.MyRoute;
import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.example.a14512.discover.modules.routeplan.mode.entity.ScenicCommentUser;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author 14512 on 2017/8/15.
 */

public interface ApiService {

    String BASE_URL = "http://139.199.15.137:8080/faxianbeiwork/";
    String WEATHER = "https://free-api.heweather.com/s6/weather/";

    /**
     * 获取验证码
     * @param phone
     * @param pwd
     * @return
     */
    @GET("SendMessages")
    Observable<Result<RegisterData>> getCode(@Query("phone") String phone, @Query("pssword") String pwd);

    /**
     * 注册
     * @param phone
     * @param pwd
     * @return
     */
    @GET("RegisterSevlet")
    Observable<Result<Integer>> register(@Query("phone") String phone, @Query("password") String pwd);

    /**
     * 验证登录
     * @param phone
     * @param pwd
     * @return
     */
    @GET("IndexServlet")
    Observable<Result<String>> isLogin(@Query("phone") String phone, @Query("password") String pwd);

    /**
     * 获取七牛云token
     * @return Result<String>
     * */
    @POST("CreateTokenServlet")
    Observable<Result<String>> getToken();

    /**
     * 获取天气信息
     * @param location
     * @param key
     * @return
     */
    @GET("now")
    Observable<WeatherData> getWeather(@Query("location") String location,
                                       @Query("key") String key);

    /**
     * 获取路线规划
     * @param startLng
     * @param startLat
     * @param endLng
     * @param endLat
     * @param startName
     * @param endName
     * @param seTime
     * @param personSelect
     * @param tfSelect
     * @param onePlace
     * @param phone
     * @return
     */
    @GET("LinePlanServlet")
    Observable<Result<ArrayList<Scenic>>> getScenic(@Query("startLng") double startLng,
                                                    @Query("startLat") double startLat,
                                                    @Query("endLng") double endLng,
                                                    @Query("endLat") double endLat,
                                                    @Query("startName") String startName,
                                                    @Query("endName") String endName,
                                                    @Query("startTime") String startTime,
                                                    @Query("seTime") long seTime,
                                                    @Query("personSelect") int personSelect,
                                                    @Query("tfSelect") int tfSelect,
                                                    @Query("onePlace") int onePlace,
                                                    @Query("userPhone") String phone);

    /**
     * 刷新某一个景点
     * @param changePlace
     * @param lastPlace
     * @param nextPlace
     * @param personSelect
     * @return
     */
    @GET("ChangePlaceServlet")
    Observable<Result<Scenic>> changeOneScenic(@Query("changePlace") String changePlace,
                                       @Query("lastPlace") String lastPlace,
                                       @Query("nextPlace") String nextPlace,
                                       @Query("personSelect") int personSelect);

    /**
     * 关注景点
     * @param isFollow
     * @param phone
     * @param placeName
     * @return
     */
    @GET("PlaceFollowServlet")
    Observable<Result<Integer>> followScenic(@Query("placeFound") int isFollow,
                                    @Query("userPhone") String phone,
                                    @Query("placeName") String placeName);

    /**
     * 修改个人信息
     * @param userPhone
     * @param userPhoto
     * @param userName
     * @param userSex
     * @param userBirth
     * @param userSchool
     * @param userSign
     * @param userEmail
     * @return
     */
    @GET("ModifyInformationServlet")
    Observable<Result<Integer>> changeUserData(@Query("userPhone") String userPhone,
                                               @Query("userPhoto") String userPhoto,
                                               @Query("userName") String userName,
                                               @Query("userSex") String userSex,
                                               @Query("userBirthday") String userBirth,
                                               @Query("userSchool") String userSchool,
                                               @Query("userSign") String userSign,
                                               @Query("userEmail") String userEmail);

    /**
     * 获取我的关注
     * @param userPhone
     * @return
     */
    @GET("MyFollowServlet")
    Observable<Result<ArrayList<Scenic>>> getMyFollow(@Query("userPhone") String userPhone);

    /**
     * 获取我的路线
     * @param userPhone
     * @return
     */
    @GET("MyRouteServlet")
    Observable<Result<ArrayList<MyRoute>>> getMyRoute(@Query("userPhone") String userPhone);

    /**
     * 评分
     * @param userPhone
     * @param placeName
     * @param firstScore
     * @param secondScore
     * @param thirdScore
     * @param fourthScore
     * @return
     */
    @GET("ScoreServlet")
    Observable<Result<Integer>> setCommentStar(@Query("userPhone") String userPhone,
                                               @Query("placeName") String placeName,
                                               @Query("firstScore") int firstScore,
                                               @Query("secondScore") int secondScore,
                                               @Query("thirdScore") int thirdScore,
                                               @Query("fourthScore") int fourthScore);

    /**
     * 获取评论评分
     * @param placeName
     * @return
     */
    @GET("UserCommentServlet")
    Observable<Result<ArrayList<ScenicCommentUser>>> getCommentScore(@Query("placeName") String placeName);

    /**
     * 获取摇一摇景点
     * @return
     */
    @GET("NearbyServlet")
    Observable<Result<Scenic>> getShakeScenic();

    /**
     * 个性化设置
     * @param phone
     * @param found
     * @return
     */
    @GET("PersonalServlet")
    Observable<Result<Object>> setPersonality(@Query("userPhone") String phone,
                                              @Query("personFound") String found);

    /**
     * 添加历史路线
     * @param phone
     * @param routePlace
     * @return
     */
    @GET("AddHistroyPlaceServlet")
    Observable<Result<Integer>> addHistoricRoute(@Query("userPhone") String phone,
                                                 @Query("routePlace") String routePlace);

    /**
     * 保存我的路线
     * @param routeName
     * @param placeNum
     * @param distance
     * @param coast
     * @param routeTime
     * @param phone
     * @param information
     * @return
     */
    @GET("RouteResultServlet")
    Observable<Result<String>> addMyRoute(@Query("routeName") String routeName,
                                          @Query("placeNumber") int placeNum,
                                          @Query("allDistance") int distance,
                                          @Query("allCoast") int coast,
                                          @Query("routeTime") int routeTime,
                                          @Query("userPhone") String phone,
                                          @Query("routeInformation") String information);

    /**
     * 结束路线
     * @param phone
     * @param routeName
     * @return
     */
    @GET("EndRouteServlet")
    Observable<Result<Integer>> endRoute(@Query("userPhone") String phone,
                                         @Query("routeName") String routeName);

    /**
     * 添加已去景点
     * @param phone
     * @param historicPlace 多个地点用.链接
     * @return
     */
    @GET("AddHistroyPlaceServlet")
    Observable<Result<Integer>> addHistoricScenic(@Query("userPhone") String phone,
                                                  @Query("routePlace") String historicPlace);

    /**
     * 获取用户信息
     * @param phone
     * @return
     */
    @GET("UserInformationServlet")
    Observable<Result<UserInfo>> getUserInfo(@Query("userPhone") String phone);

    /**
     * 获取重庆市所有景点信息
     * @return
     */
    @GET("AroundingServlet")
    Observable<Result<ArrayList<Scenic>>> getAroundScenic();

}
