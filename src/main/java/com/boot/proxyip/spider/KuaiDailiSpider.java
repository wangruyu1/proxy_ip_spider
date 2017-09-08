package com.boot.proxyip.spider;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boot.proxyip.entity.ProxyIp;
import com.boot.proxyip.parser.KuaiDailiParser;
import com.boot.proxyip.service.ProxyIpService;

@Component
public class KuaiDailiSpider extends AbstractSpider {
	private static final Logger LOGGER = LoggerFactory.getLogger(KuaiDailiSpider.class);
	private static final String ENTRY_URL = "http://www.kuaidaili.com/free/inha/";
	private static final int MAX_THREAD_POOL_NUM = 1;
	private static final int MAX_QUEUE_TASK_NUM = 100;
	private static ScheduledThreadPoolExecutor threadPool = (ScheduledThreadPoolExecutor) Executors
			.newScheduledThreadPool(MAX_THREAD_POOL_NUM);
	private volatile static int startIndex = 0;
	private static final int END_FLAG = -99;

	@Autowired
	private KuaiDailiParser kuaiDailiParser;
	@Autowired
	private ProxyIpService proxyIpService;

	@Override
	public void spider() {
		LOGGER.info("开始爬取kuaidaili:" + ENTRY_URL);
		while (startIndex >= 0) {
			while (threadPool.getQueue().size() >= MAX_QUEUE_TASK_NUM) {
				LOGGER.info("解析kudaidaili太慢.");
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			threadPool.schedule(new KudaiDaiLiSpiderTask(), 10, TimeUnit.MILLISECONDS);
		}

	}

	public class KudaiDaiLiSpiderTask implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String pageUrl = ENTRY_URL + (++startIndex);
			LOGGER.info("开始爬取:" + pageUrl);
			List<ProxyIp> ips = kuaiDailiParser.parseForList(pageUrl);
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
