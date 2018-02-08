package com.example.a14512.discover.modules.main.mode.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author 14512 on 2018/2/3
 */

public class WeatherData {

    /**
    basic: - {
        cid: "CN101040100",
                location: "重庆",
                parent_city: "重庆",
                admin_area: "重庆",
                cnty: "中国",
                lat: "29.56376076",
                lon: "106.55046082",
                tz: "+8.0"
    },
    update: - {
        loc: "2018-02-03 20:07",
                utc: "2018-02-03 12:07"
    },
    status: "ok",
    now: - {
        cloud: "50",
                cond_code: "104",
                cond_txt: "阴",
                fl: "1",
                hum: "42",
                pcpn: "0.0",
                pres: "1027",
                tmp: "9",
                vis: "7",
                wind_deg: "130",
                wind_dir: "东南风",
                wind_sc: "微风",
                wind_spd: "10"
    }
    */

    @SerializedName("HeWeather6")
    public ArrayList<Weather> heWeather;

    public class Weather {
        @SerializedName("basic")
        public Basic mBasic;

        @SerializedName("update")
        public Update mUpdate;

        @SerializedName("now")
        public WeatherNow mNow;

        public class WeatherNow {
            public String cloud;
            @SerializedName("cond_code")
            public String condCode;
            @SerializedName("cond_txt")
            public String condTxt;
            public String fl;
            public String hum;
            public String pcpn;
            public String pres;
            public String tmp;
            public String vis;
            @SerializedName("wind_deg")
            public String windDeg;
            @SerializedName("wind_dir")
            public String windDir;
            @SerializedName("wind_sc")
            public String windSc;
            @SerializedName("wind_spd")
            public String windSpd;
        }

        public class Basic {
            public String cid;
            public String location;
            public String parent_city;
            public String admin_area;
            public String cnty;
            public String lat;
            public String lon;
            public String tz;

        }

        public class Update {
            public String loc;
            public String utc;
        }
    }
}
