package com.boot.proxyip.util;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupUtil {
	public static Document getDocumentByUrl(String url, Map<String, String>... headers) {
		return Jsoup.parse(HttpUtil.getUrlContent(url, headers));
	}

}
