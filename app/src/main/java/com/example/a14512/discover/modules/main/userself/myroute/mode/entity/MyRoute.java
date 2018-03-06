package com.example.a14512.discover.modules.main.userself.myroute.mode.entity;

import com.example.a14512.discover.modules.routeplan.mode.entity.Scenic;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/9
 */

public class MyRoute implements Serializable {

    /**
     * route_time : 0
     * route_name : com.example.a14512.discover.modules.routeplan.mode.entity.Scenic@7c6d54c-com.example.a14512.discover.modules.routeplan.mode.entity.Scenic@96e2995
     * routeInformation : [{"place_name":"我的位置","place_time":0,"place_popularity":0,"place_price":0,"place_lat":29.537006,"place_lng":106.6165,"times":"-2"},{"place_photo":"http:\\/\\/hiphotos.bdimg.com\\/lvpics\\/pic\\/item\\/d833c895d143ad4bc54dab8982025aafa40f0613.jpg","place_name":"洋人街游乐场","place_time":64,"place_popularity":48,"place_price":48,"place_introduce":"游乐园 亲子好去处","place_lat":34.326984,"place_lng":118.678375,"times":"1"},{"place_photo":"http:\\/\\/hiphotos.bdimg.com\\/lvpics\\/pic\\/item\\/4afbfbedab64034f092474d6aec379310b551d70.jpg","place_name":"儿童乐园","place_time":61,"place_popularity":40,"place_price":40,"place_introduce":"游乐园","place_lat":34.22016,"place_lng":118.57085,"times":"1"},{"place_name":"观音桥","place_time":0,"place_popularity":0,"place_price":0,"place_lat":29.478386,"place_lng":106.06864,"times":"2"}]
     * all_coast : 88
     * all_distance : 3284
     * route_found : 1
     * place_number : 2
     */

    private String route_time;
    private String route_name;
    private int all_coast;
    private int all_distance;
    private int route_found;
    private String place_number;
    private ArrayList<Scenic> routeInformation;

    public boolean isComment = false;

    public static MyRoute objectFromData(String str) {

        return new Gson().fromJson(str, MyRoute.class);
    }

    public String getRoute_time() {
        return route_time;
    }

    public void setRoute_time(String route_time) {
        this.route_time = route_time;
    }

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public int getAll_coast() {
        return all_coast;
    }

    public void setAll_coast(int all_coast) {
        this.all_coast = all_coast;
    }

    public int getAll_distance() {
        return all_distance;
    }

    public void setAll_distance(int all_distance) {
        this.all_distance = all_distance;
    }

    public int getRoute_found() {
        return route_found;
    }

    public void setRoute_found(int route_found) {
        this.route_found = route_found;
    }

    public String getPlace_number() {
        return place_number;
    }

    public void setPlace_number(String place_number) {
        this.place_number = place_number;
    }

    public ArrayList<Scenic> getRouteInformation() {
        return routeInformation;
    }

    public void setRouteInformation(ArrayList<Scenic> routeInformation) {
        this.routeInformation = routeInformation;
    }

}
