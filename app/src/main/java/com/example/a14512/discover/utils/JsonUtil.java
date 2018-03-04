package com.example.a14512.discover.utils;

import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/24
 * JSON工具类
 */

public class JsonUtil {

    /**
     * 将ArrayList数组转换成JSON字符串
     * @param arrayList
     * @return
     */
    public static String toJSONString(ArrayList<Scenic> arrayList) {
//        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
//            jsonObject.put("info", "success");
//            jsonObject.put("status", 200);
            for(int i = 0; i < arrayList.size(); i++) {
                JSONObject object = new JSONObject();
                Scenic scenic = arrayList.get(i);
                object.put("place_photo", scenic.img);
                object.put("place_name", scenic.name);
                object.put("place_time", scenic.time);
                object.put("place_popularity", scenic.monthAver);
                object.put("place_price", scenic.peopleAver);
                object.put("place_introduce", scenic.content);
                object.put("place_lat", scenic.latitude);
                object.put("place_lng", scenic.longitude);
                object.put("times", scenic.timeType);
                jsonArray.put(object);
            }
//            jsonObject.put(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }
}
