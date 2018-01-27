package com.example.a14512.discover.network.cookie;

import com.example.a14512.discover.network.cookie.cache.CookieCache;
import com.example.a14512.discover.network.cookie.persistence.CookiePersistor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by 14512 on 2017/8/16.
 */

public class PersistenceCookieJar implements ClearableCookieJar {

    private CookieCache cache;
    private CookiePersistor persistor;

    public PersistenceCookieJar(CookieCache cache, CookiePersistor persistor) {
        this.cache = cache;
        this.persistor = persistor;
        this.cache.addAll(persistor.loadAll());
    }
    @Override
    public void clearSession() {
        cache.clear();
        cache.addAll(persistor.loadAll());
    }

    @Override
    public void clear() {
        cache.clear();
        persistor.clear();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cache.addAll(cookies);
        persistor.saveAll(filterPersistenceCookie(cookies));
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookiesToRemove = new ArrayList<>();
        List<Cookie> validCookies = new ArrayList<>();
        for (Iterator<Cookie> iterator = cache.iterator(); iterator.hasNext(); ) {
            Cookie currentCookie = iterator.next();
            if (isCookieWxpired(currentCookie)){
                cookiesToRemove.add(currentCookie);
                iterator.remove();
            } else if (currentCookie.matches(url)) {
                validCookies.add(currentCookie);
            }
        }
        persistor.removeAll(cookiesToRemove);
        return validCookies;
    }

    private static boolean isCookieWxpired(Cookie cookie) {
        return cookie.expiresAt()< System.currentTimeMillis();
    }

    private static List<Cookie> filterPersistenceCookie(List<Cookie> cookies) {
        List<Cookie> persistenceCookies = new ArrayList<>();
        for (Cookie cookie : cookies) {
            if (cookie.persistent()) {
                persistenceCookies.add(cookie);
            }
        }
        return persistenceCookies;
    }

}
