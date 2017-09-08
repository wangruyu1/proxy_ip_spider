package com.boot.proxyip.spider;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boot.proxyip.entity.ProxyIp;

public abstract class AbstractSpider implements Spider {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSpider.class);
	private static HttpRequestRetryHandler NO_RETRY = null;
	static {
		NO_RETRY = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= 1) {
					return false;
				}
				if (exception instanceof InterruptedIOException) {
					return false;
				}
				if (exception instanceof UnknownHostException) {
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {
					return false;
				}
				if (exception instanceof SSLException) {
					// SSL handshake exception
					return false;
				}
				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					// Retry if the request is considered idempotent
					return true;
				}
				return false;
			}
		};
	}

	public static boolean verifyIp(ProxyIp ip) {
		try {
			HttpClient httpClient = HttpClients.custom().setProxy(new HttpHost(ip.getIp(), ip.getPort()))
					.setRetryHandler(NO_RETRY).build();
			HttpGet httpGet = new HttpGet("https://www.baidu.com");
			HttpResponse response = httpClient.execute(httpGet);
			int code = response.getStatusLine().getStatusCode();
			if (code >= HttpStatus.SC_OK && code < HttpStatus.SC_BAD_REQUEST) {
				LOGGER.info("ip可用" + ip);
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("请求发生异常.", e);
		}
		return false;
	}

}
