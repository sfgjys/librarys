package com.minji.librarys.http;

import com.minji.librarys.base.MyApplication;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by user on 2016/7/20.
 */
public class CookiesManager implements CookieJar {

    /**
     * 此对象是对Cookie进行保存管理的对象
     */
    private final PersistentCookieStore cookieStore = new PersistentCookieStore(MyApplication.getContext());

    /**
     * @param url
     * @param cookies 是进行网络请求结束后响应头中的Cookie对象集合
     */
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        System.out.println("saveFromResponse++++++++++++++++++++++++++");

        System.out.println("fragment: " + url.fragment());
        System.out.println("host: " + url.host());
        System.out.println("password: " + url.password());
        System.out.println("query: " + url.query());
        System.out.println("scheme: " + url.scheme());
        System.out.println("username: " + url.username());
        System.out.println("pathSegments: " + url.pathSegments());

        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
                System.out.println("value: " + item.value());
                System.out.println("domain: " + item.domain());
                System.out.println("name: " + item.name());
                System.out.println("path: " + item.path());
            }
        }
    }

    /**
     * @param url
     * @return 此方法用参数url去PersistentCookieStore对象中去获取相对应的Cookie集合对象，并返回给 进行网络请求时的请求头
     */
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        System.out.println("loadForRequest=======================");

        System.out.println("fragment: " + url.fragment());
        System.out.println("host: " + url.host());
        System.out.println("password: " + url.password());
        System.out.println("query: " + url.query());
        System.out.println("scheme: " + url.scheme());
        System.out.println("username: " + url.username());
        System.out.println("pathSegments: " + url.pathSegments());

//        if (!url.pathSegments().contains("login")) {
        List<Cookie> cookies = cookieStore.get(url);
//        }
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                System.out.println("value: " + item.value());
                System.out.println("domain: " + item.domain());
                System.out.println("name: " + item.name());
                System.out.println("path: " + item.path());
            }
        }

        return cookies;
    }
}
