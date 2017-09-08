package com.boot.proxyip.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.boot.proxyip.entity.ProxyIp;
import com.boot.proxyip.mapper.ProxyIpMapper;
import com.boot.proxyip.spider.AbstractSpider;

@Component
public class ProxyIpTask {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProxyIpTask.class);
	private static Date lastCarryOutTime = null;
	private static final long HALF_AN_HOUR_MINUTES = 30;
	private static final long MINUTE_DIVISOR = 60 * 60 * 1000;

	@Autowired
	ProxyIpMapper proxyIpMapper;

	@Scheduled(cron = "0 * * * * ? ")
	public void checkProxyIp() {
		if (lastCarryOutTime == null) {
			lastCarryOutTime = new Date();
		} else {
			long diff = System.currentTimeMillis() - lastCarryOutTime.getTime();
			if (diff / MINUTE_DIVISOR != HALF_AN_HOUR_MINUTES) {
				return;
			}
		}
		lastCarryOutTime = new Date();
		LOGGER.info("-----------------------开始检测代理ip有效性------------------------------------");
		List<ProxyIp> pis = proxyIpMapper.queryAll();
		pis.forEach(pi -> {
			if (!AbstractSpider.verifyIp(pi)) {
				proxyIpMapper.modifyValid(false, pi.getId());
			}
		});
		LOGGER.info("-----------------------检测代理ip有效性结束------------------------------------");
	}
}
