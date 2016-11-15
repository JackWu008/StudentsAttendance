package com.example.get;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class MyCookieJar implements CookieJar {
	List<Cookie> cookieStore = new ArrayList<>();

	@Override
	public List<Cookie> loadForRequest(HttpUrl arg0) {
		List<Cookie> cookies = new ArrayList<>();
		for (Cookie cookie : cookieStore)
			if (cookie.matches(arg0))
				cookies.add(cookie);
		return cookies;
	}

	@Override
	public void saveFromResponse(HttpUrl arg0, List<Cookie> arg1) {
		cookieStore.addAll(arg1);
	}

}
