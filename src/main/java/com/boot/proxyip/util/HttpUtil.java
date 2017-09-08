package com.boot.proxyip.util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boot.proxyip.constant.AppConstant;

public class HttpUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
	public static final long RELEASE_CONNECTION_WAIT_TIME = 5000;// 监控连接间隔
	private static PoolingHttpClientConnectionManager httpClientConnectionManager = null;
	private static LaxRedirectStrategy redirectStrategy = null;
	private static HttpRequestRetryHandler myRetryHandler = null;
	private static SSLConnectionSocketFactory sslConnectionSocketFactory = null;
	private static final int MAX_SLEEP_TIME = 100;
	private static final int REQUEST_MAX_TIMES_SHORT_TIME = 100;
	private static final int FIX_SLEEP_TIME = 100;

	private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

	static {
		initHttpClient();
		new IdleConnectionMonitorThread().start();
	}

	public static void initHttpClient() {
		try {
			// 重定向策略初始化
			redirectStrategy = new LaxRedirectStrategy();
			// 请求重试机制
			myRetryHandler = new HttpRequestRetryHandler() {
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
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			sslConnectionSocketFactory = new SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", new PlainConnectionSocketFactory())//
					.register("https", sslConnectionSocketFactory)//
					.build();
			// 创建httpclient连接池
			httpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
			// 设置连接池最大数量
			httpClientConnectionManager.setMaxTotal(AppConstant.HTTPCLIENT_CONNECTION_COUNT);
			// 设置单个路由最大连接数量
			httpClientConnectionManager.setDefaultMaxPerRoute(AppConstant.HTTPCLIENT_MAXPERROUTE_COUNT);
		} catch (Exception e) {
			LOGGER.error("初始化httpclient连接池失败.", e);
		}

	}

	public static CloseableHttpClient getHttpClient() {
		// HttpHost ip = ConfigFileUtil.getIpByUsedTime();
		// LOGGER.info("使用代理->" + ip.getHostName() + ":" + ip.getPort());
		// 全局请求参数配置
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(MAX_SLEEP_TIME));
		} catch (InterruptedException e) {
			LOGGER.error("挂起线程" + Thread.currentThread().getName() + "失败.", e);
		}
		Integer count = threadLocal.get();
		if (count == null) {
			count = 1;
		} else {
			++count;
		}
		threadLocal.set(count);
		count = count % REQUEST_MAX_TIMES_SHORT_TIME;
		if (count == 0) {
			try {
				Thread.sleep(FIX_SLEEP_TIME);
			} catch (InterruptedException e) {
				LOGGER.error("挂起线程" + Thread.currentThread().getName() + "失败.", e);
			}
		}
		RequestConfig requestConfig = RequestConfig.custom()//
				.setConnectTimeout(AppConstant.HTTPCLIENT_CONNECT_TIMEOUT)//
				.setSocketTimeout(AppConstant.HTTPCLIENT_SOCKET_TIMEOUT)//
				.setCookieSpec(CookieSpecs.IGNORE_COOKIES)// 忽略cookie
				.setConnectionRequestTimeout(AppConstant.HTTPCLIENT_REQUEST_TIMEOUT)//
				// .setProxy(ip)//
				.build();
		// 连接池配置
		CloseableHttpClient httpClient = HttpClients.custom()//
				.setSSLSocketFactory(sslConnectionSocketFactory)//
				.setConnectionManager(httpClientConnectionManager)//
				.setDefaultRequestConfig(requestConfig)//
				.setRedirectStrategy(redirectStrategy)//
				.setRetryHandler(myRetryHandler)//
				.build();

		return httpClient;
	}

	public static String getUrlContent(String urlString, Map<String, String>... headers) {
		String src = "";
		if (null == urlString || urlString.isEmpty() || !urlString.startsWith("http")) {
			return src;
		}
		CloseableHttpResponse response = null;
		HttpGet httpGet = null;
		urlString = urlString.trim();
		// 转化String url为URI,解决url中包含特殊字符的情况
		try {
			URL url = new URL(urlString);
			URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
			httpGet = new HttpGet(uri);
			setCommonHeaders(httpGet);
			// 额外的header
			if (headers.length > 0) {
				for (Map.Entry<String, String> header : headers[0].entrySet()) {
					if (httpGet.containsHeader(header.getKey())) {
						// 覆盖原始头
						httpGet.setHeader(header.getKey(), header.getValue());
					} else {
						httpGet.addHeader(header.getKey(), header.getValue());
					}
				}
			}
			HttpClient httpClient = getHttpClient();
			response = (CloseableHttpResponse) httpClient.execute(httpGet);

			int statusCode = response.getStatusLine().getStatusCode();
			switch (statusCode) {
			case 200:
				return EntityUtils.toString(response.getEntity());
			case 400:
				LOGGER.error("下载400错误代码，请求出现语法错误" + urlString);
				break;
			case 403:
				LOGGER.error("下载403错误代码，资源不可用" + urlString);
				break;
			case 404:
				LOGGER.error("下载404错误代码，无法找到指定资源地址" + urlString);
				break;
			case 503:
				LOGGER.error("下载503错误代码，服务不可用" + urlString);
				break;
			case 504:
				LOGGER.error("下载504错误代码，网关超时" + urlString);
				break;
			default:
				LOGGER.error("未处理的错误,code=" + statusCode);
			}

		} catch (Exception e) {
			LOGGER.error("请求发生错误.");
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					LOGGER.error("关闭响应错误.", e);
				}
			}
			httpGet.abort(); // 结束后关闭httpGet请求
		}

		return src;
	}

	private static void setCommonHeaders(HttpGet httpGet) {
		httpGet.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		httpGet.addHeader("Connection", "keep-alive");
		httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpGet.addHeader("Accept-Encoding", "gzip, deflate, br");
		httpGet.addHeader("User-Agent", ConfigFileUtil.getNextUserAgent());
	}

	/**
	 * 连接处理
	 * 
	 * @author 王如雨
	 *
	 */
	public static class IdleConnectionMonitorThread extends Thread {

		private volatile boolean shutdown;

		@Override
		public void run() {
			try {
				while (!shutdown) {
					synchronized (this) {
						wait(RELEASE_CONNECTION_WAIT_TIME);
						// Close expired connections
						httpClientConnectionManager.closeExpiredConnections();
						// that have been idle longer than 30 sec
						httpClientConnectionManager.closeIdleConnections(30, TimeUnit.SECONDS);
					}
				}
			} catch (InterruptedException ex) {
				LOGGER.error("释放连接池连接出错.");
			}
		}

		public void shutdown() {
			shutdown = true;
			synchronized (this) {
				notifyAll();
			}
		}

	}

}
