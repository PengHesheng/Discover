package com.example.a14512.discover.network.cookie.cache;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import okhttp3.Cookie;

/**
 * Created by 14512 on 2017/8/16.
 */

public class SetCookieCache implements CookieCache  {

    private Set<IdentifiableCookie> cookies;

    public SetCookieCache() {
        this.cookies = new HashSet<>();
    }

    @Override
    public void addAll(Collection<Cookie> cookies) {
        for (IdentifiableCookie cookie : IdentifiableCookie.decorateAll(cookies)) {
            this.cookies.remove(cookie);
            this.cookies.add(cookie);
        }
    }

    @Override
    public void clear() {
        cookies.clear();
    }

    @Override
    public Iterator<Cookie> iterator() {
        return new SetCookieCacheIterator();
    }

    private class SetCookieCacheIterator implements Iterator<Cookie> {

        private Iterator<IdentifiableCookie> iterator;

        public SetCookieCacheIterator() {
            this.iterator = cookies.iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Cookie next() {
            return iterator.next().getCookie();
        }

        @Override
        public void remove() {
            iterator.remove();
        }
    }
}
