package com.boot.proxyip.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.boot.proxyip.entity.ProxyIp;
import com.boot.proxyip.util.DateUtil;
import com.boot.proxyip.util.JsoupUtil;

@Component
public class XiCiParser extends AbstractParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(XiCiParser.class);

	@Override
	public ProxyIp parse(String url) {
		return null;
	}

	@Override
	public List<ProxyIp> parseForList(String url) {
		List<ProxyIp> ips = new ArrayList<>();
		Document doc = JsoupUtil.getDocumentByUrl(url);
		Elements es = doc.select("table[id=ip_list] tr[class]");
		for (Element e : es) {
			try {
				ProxyIp pi = new ProxyIp();
				Elements tdEs = e.select("td");
				pi.setCountry(tdEs.get(0).select("img").attr("alt"));
				pi.setIp(tdEs.get(1).text());
				pi.setPort(Integer.parseInt(tdEs.get(2).text()));
				pi.setAddress(tdEs.get(3).text());
				pi.setAnonymous(tdEs.get(4).text());
				pi.setType(tdEs.get(5).text());
				pi.setSpeed(tdEs.get(6).select("div").attr("title"));
				pi.setBkField1(tdEs.get(7).select("div").attr("title"));
				pi.setSurvivalTime(tdEs.get(8).text());
				pi.setCheckTime(tdEs.get(9).text());
				pi.setValid(true);
				pi.setSrc("西刺");
				ips.add(pi);
			} catch (Exception e1) {
				LOGGER.error("解析xici字段错误.url=" + url, e1);
			}
		}
		return ips;
	}

}
