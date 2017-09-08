package com.boot.proxyip.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.boot.proxyip.entity.ProxyIp;
import com.boot.proxyip.util.JsoupUtil;

@Component
public class KuaiDailiParser extends AbstractParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(KuaiDailiParser.class);
	private static final HashMap<String, String> headers = new HashMap<>();
	static {
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "zh-CN,zh;q=0.8");
		headers.put("Host", "www.kuaidaili.com");
		headers.put("Referer", "http://www.kuaidaili.com");
		headers.put("Proxy-Connection", "keep-alive");
		headers.put("Upgrade-Insecure-Requests", "1");
		headers.put("Cookie",
				"yd_cookie=f53bc8a3-4fd8-487b4927132a12397de43b16b71e5edb749f; channelid=0; sid=1504511626870753; Hm_lvt_7ed65b1cc4b810e9fd37959c9bb51b31=1504266445,1504507634,1504511948; Hm_lpvt_7ed65b1cc4b810e9fd37959c9bb51b31=1504512279; _ga=GA1.2.1422194031.1504266445; _gid=GA1.2.1177562890.1504507634; _ydclearance=092774bf157e52d01d66ca41-9042-41be-8722-df322d45b0f4-1504524195");
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
	}

	@Override
	public ProxyIp parse(String url) {
		return null;
	}

	@Override
	public List<ProxyIp> parseForList(String url) {
		List<ProxyIp> ips = new ArrayList<>();
		Document doc = JsoupUtil.getDocumentByUrl(url, headers);
		Elements es = doc.select("div[id=list] tbody tr");
		for (Element e : es) {
			try {
				ProxyIp pi = new ProxyIp();
				Elements tdEs = e.select("td");
				pi.setIp(tdEs.get(0).text());
				pi.setPort(Integer.parseInt(tdEs.get(1).text()));
				pi.setAnonymous(tdEs.get(2).text());
				pi.setType(tdEs.get(3).text());
				pi.setAddress(tdEs.get(4).text());
				pi.setSpeed(tdEs.get(5).text());
				pi.setCheckTime(tdEs.get(6).text());
				pi.setValid(true);
				pi.setSrc("kuaidaili");
				ips.add(pi);
			} catch (Exception e1) {
				LOGGER.error("解析xici字段错误.url=" + url, e1);
			}
		}
		return ips;
	}

}
