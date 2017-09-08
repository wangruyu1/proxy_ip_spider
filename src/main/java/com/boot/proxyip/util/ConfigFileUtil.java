package com.boot.proxyip.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * --------------------------- 创建者: 王如雨 ------------------------
 * --------------------------- 创建时间: 2017年8月25日 下午2:55:58 -----
 * --------------------------- 版本: v1 ---------------------------
 * ---------------------------------------------------------------
 * -----------功能说明: 获取代理ip
 */
public class ConfigFileUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigFileUtil.class);
	private static final String IP_FILE_PATH = "config/proxy_ip";
	private static final String USER_AGENT_FILE_PATH = "config/user_agent";
	private static List<HttpHost> ips = new ArrayList<>();
	private static List<String> userAgents = new ArrayList<>();
	private static HttpHost localHost = new HttpHost("localhost", 9090);

	private volatile static boolean ipReaded = false;
	private volatile static int currentIpIndex = -1;
	private volatile static int currentUserAgentIndex = -1;
	private volatile static int ipUsedCount = 0;
	private final static int IP_MAX_USED_COUNT_ONCE = 10;

	private static Object ipIndexLock = new Object();

	static {
		// 读取ip
		readProxyIps();
		// 读取useragent
		readUserAgent();
	}

	public static List<HttpHost> readProxyIps() {
		Resource resource = new ClassPathResource(IP_FILE_PATH);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				if ("".equals(line.trim())) {
					continue;
				}
				String[] ip = line.split(":");
				HttpHost hh = new HttpHost(ip[0].trim(), Integer.valueOf(ip[1].trim()));
				ips.add(hh);
			}
			LOGGER.info("proxy_ip读取完毕.");
		} catch (Exception e) {
			LOGGER.error("ip文件读取失败.");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOGGER.error("关闭流错误.", e);
				}
			}
		}

		return ips;
	}

	public static HttpHost getRandomIp() {
		if (ips.size() == 0) {
			return localHost;
		}
		int ipIndex = ThreadLocalRandom.current().nextInt(ips.size());
		return ips.get(ipIndex);
	}

	public static HttpHost getOrderedIp() {
		if (ips.size() == 0) {
			return localHost;
		}
		synchronized (ipIndexLock) {
			currentIpIndex = (++currentIpIndex) % ips.size();
		}
		return ips.get(currentIpIndex);
	}

	public static HttpHost getIpByUsedTime() {
		if (ips.size() == 0) {
			return localHost;
		}
		synchronized (ipIndexLock) {
			if ((++ipUsedCount) % IP_MAX_USED_COUNT_ONCE == 0) {
				ipUsedCount = ThreadLocalRandom.current().nextInt(IP_MAX_USED_COUNT_ONCE / 4);
				currentIpIndex = (++currentIpIndex) % ips.size();
			}
		}
		return ips.get(currentIpIndex);
	}

	private void readProxyIp() {
		if (ips.size() == 0 && !ipReaded) {
			synchronized (ConfigFileUtil.class) {
				if (ips.size() == 0 && !ipReaded) {
					LOGGER.info("正在读取ip......");
					readProxyIps();
					ipReaded = true;
				}
			}
		}
	}

	private static void readUserAgent() {
		Resource resource = new ClassPathResource(USER_AGENT_FILE_PATH);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				if ("".equals(line.trim()) || "#".equals(line.trim().charAt(0) + "")) {
					continue;
				}
				userAgents.add(line);
			}
			LOGGER.info("读取useragent完成.");
		} catch (Exception e) {
			LOGGER.error("读取useragent错误.", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOGGER.error("关闭流错误.", e);
				}
			}
		}
	}

	public static String getNextUserAgent() {
		return userAgents.get(ThreadLocalRandom.current().nextInt(userAgents.size()));
	}

}
