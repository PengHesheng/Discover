package com.example.a14512.discover.network;

import com.example.a14512.discover.BuildConfig;
import com.example.a14512.discover.DiscoverApplication;
import com.example.a14512.discover.network.cookie.ClearableCookieJar;
import com.example.a14512.discover.network.cookie.PersistenceCookieJar;
import com.example.a14512.discover.network.cookie.cache.SetCookieCache;
import com.example.a14512.discover.network.cookie.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 将网络请求统一放在这里，方便管理，减少Model的代码量
 * @author 14512 on 2017/8/15.
 */

public class RetrofitHelper {

    private static Retrofit retrofit = null;
    private static ApiService apiService = null;

    private RetrofitHelper() {
        apiService = build(ApiService.BASE_URL).create(ApiService.class);
    }

    public static RetrofitHelper getInstance() {
        return new RetrofitHelper();
    }

    public static ApiService getApiService() {
        if (apiService == null) {
            throw new NullPointerException("get apiService must be called after init");
        }
        return apiService;
    }

    public static <T> T createApi(String url, Class<T> tClass) {
        return build(url).create(tClass);
    }

    private static Retrofit build(String baseUrl) {
        if (retrofit == null) {
            synchronized (Retrofit.class) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        // 使用OkHttp Client
                        .client(buildOkHttpClient())
                        // 集成RxJava处理
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        // 集成Gson转换器
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }

    /**
     * 构建OkHttpClient
     * @return
     */
    private static OkHttpClient buildOkHttpClient() {
        //持久化cookie
        ClearableCookieJar cookieJar = new PersistenceCookieJar(new SetCookieCache(),
                new SharedPrefsCookiePersistor(DiscoverApplication.getContext()));
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG?HttpLoggingInterceptor.Level.HEADERS:HttpLoggingInterceptor.Level.NONE);
        return new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor)
                .cookieJar(cookieJar)
                .retryOnConnectionFailure(true)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 构建GSON转换器
     * @return GsonConverterFactory
     */
    private static GsonConverterFactory buildGsonConverterFactory(){
        GsonBuilder builder = new GsonBuilder();
        builder.setLenient();

        // 注册类型转换适配器
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return null == json ? null : new Date(json.getAsLong());
            }
        });

        Gson gson = builder.create();
        return GsonConverterFactory.create(gson);
    }

    /**
     * 网络请求统一处理
     * */
//    public Observable<Gank> getDouBanTop250(int start, int count) {
//        return apiService.getTop250(start, count)
//                .compose(SchedulerTransformer.transformer())
//                .map(new ServiceResponseFun<>());
//
//    }


   /**
    * 示例，外层网络请求
    *
    *RetrofitHelper.getInstance().getDouBanTop250(0,20)
    *           .subscribe(new ApiSubscriber<Gank>(getContext(), true, false) {
    *    @Override
    *    public void onNext(Gank value) {

    *    }
    *});
    *
    */

}
