package com.example.a14512.discover.network.cookie;

import okhttp3.CookieJar;

/**
 * Created by 14512 on 2017/8/16.
 */

public interface ClearableCookieJar extends CookieJar {

    /**
     * 清除所有会话cookie，同时保留持久的cookie。
     */
    void clearSession();

    /**
     * 清除所有cookie
     */
    void clear();
}
