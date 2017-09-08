package com.boot.proxyip.spider;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boot.proxyip.entity.ProxyIp;
import com.boot.proxyip.parser.XiCiParser;
import com.boot.proxyip.service.ProxyIpService;
import com.boot.proxyip.util.HttpUtil;

@Component
public class XiCiSpider extends AbstractSpider {
	private static final Logger LOGGER = LoggerFactory.getLogger(XiCiSpider.class);
	private static final String ENTRY_URL = "http://www.xicidaili.com/wn/";
	private static final int MAX_THREAD_POOL_NUM = 10;
	private static final int MAX_QUEUE_TASK_NUM = 10;
	private static ScheduledThreadPoolExecutor threadPool = (ScheduledThreadPoolExecutor) Executors
			.newScheduledThreadPool(MAX_THREAD_POOL_NUM);
	private volatile static int startIndex = 0;
	private static final int END_FLAG = -99;

	@Autowired
	private XiCiParser xiCiParser;
	@Autowired
	private ProxyIpService proxyIpService;

	@Override
	public void spider() {
		LOGGER.info("开始爬取代理ip:" + ENTRY_URL);
		while (startIndex != END_FLAG) {
			while (threadPool.getQueue().size() >= MAX_QUEUE_TASK_NUM) {
				LOGGER.info("解析速度太慢...");
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			threadPool.schedule(new XiCiSpiderTask(), 10, TimeUnit.MILLISECONDS);
		}
		while (threadPool.getActiveCount() != 0) {

		}
		threadPool.shutdown();
	}

	public class XiCiSpiderTask implements Runnable {

		@Override
		public void run() {
			String pageUrl = ENTRY_URL + (++startIndex);
			LOGGER.info("开始爬取:" + pageUrl);
			List<ProxyIp> ips = xiCiParser.parseForList(pageUrl);
			if (ips == null || ips.size() == 0) {
				startIndex = END_FLAG;
				return;
			}
			ips.forEach(ip -> {
				if (verifyIp(ip) && !proxyIpService.existByIpAndPort(ip.getIp(), ip.getPort())) {
					proxyIpService.insert(ip);
				}
			});
		}


	}

}
