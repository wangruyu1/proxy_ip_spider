package com.boot.proxyip.main;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.boot.proxyip.parser.KuaiDailiParser;
import com.boot.proxyip.spider.KuaiDailiSpider;
import com.boot.proxyip.spider.Spider;
import com.boot.proxyip.spider.XiCiSpider;

@Component
public class MainSpider implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainSpider.class);

	private static List<Spider> spiders = new ArrayList<>();

	@Autowired
	private XiCiSpider xiCiSpider;
	@Autowired
	private KuaiDailiSpider kuaiDailiSpider;

	@Override
	public void run(String... args) throws Exception {
		init();
		spiders.forEach(spider -> {
			LOGGER.info("开始爬取各个来源代理ip...");
			spider.spider();
		});
	}

	public void init() {
//		spiders.add(xiCiSpider);
		spiders.add(kuaiDailiSpider);
	}

}
