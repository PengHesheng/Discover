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
     * route_time : 2016235
     * route_name : 呵呵
     * routeInformation : [hhoih.45]
     * all_coast : 5888
     * all_distance : 5544
     * route_found : 1
     * place_number : 455
     */

    public boolean isComment = false;
    private String route_time;
    private String route_name;
    private ArrayList<Scenic> routeInformation;
    private int all_coast;
    private int all_distance;
    private int route_found;
    private String place_number;

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

    public ArrayList<Scenic> getRouteInformation() {
        return routeInformation;
    }

    public void setRouteInformation(ArrayList<Scenic> routeInformation) {
        this.routeInformation = routeInformation;
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
}
