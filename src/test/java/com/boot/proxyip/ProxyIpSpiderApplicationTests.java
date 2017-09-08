package com.boot.proxyip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.boot.proxyip.util.HttpUtil;
import com.mysql.fabric.xmlrpc.base.Array;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ProxyIpSpiderApplicationTests {

	@Test
	public void contextLoads() throws IOException, InterruptedException {
		String command = "telnet 172.0.0.1 9090";

		Process p = Runtime.getRuntime().exec(command);

		// ##读取命令的输出信息
		InputStream is = p.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		p.waitFor();
		if (p.exitValue() != 0) {
			// 说明命令执行失败
			// 可以进入到错误处理步骤中
		}

		// ##打印输出信息
		String s = null;
		while ((s = reader.readLine()) != null) {
			System.out.println(s);
		}
	}

	@Test
	public void testUrl() throws ClientProtocolException, IOException {
		// String url = "http://www.kuaidaili.com/free/inha/1";
		String url = "http://www.kuaidaili.com/free/inha/1";
		HttpClient httpClient = HttpUtil.getHttpClient();
		HttpGet httpGet = new HttpGet(url);

		Map<String, String> headers = new HashMap<>();
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "zh-CN,zh;q=0.8");
		headers.put("Host", "www.kuaidaili.com");
		headers.put("Referer", "http://www.kuaidaili.com");
		headers.put("Proxy-Connection", "keep-alive");
		headers.put("Upgrade-Insecure-Requests", "1");
		headers.put("Cookie",
				"yd_cookie=f53bc8a3-4fd8-487b4927132a12397de43b16b71e5edb749f; _ydclearance=819b51b15d82ca2b328eb712-745f-4a8b-92be-a655639cc859-1504514834; channelid=0; sid=1504509778376535; Hm_lvt_7ed65b1cc4b810e9fd37959c9bb51b31=1504266445,1504507634; Hm_lpvt_7ed65b1cc4b810e9fd37959c9bb51b31=1504510323; _ga=GA1.2.1422194031.1504266445; _gid=GA1.2.1177562890.1504507634; _gat=1");
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
		for (Map.Entry<String, String> header : headers.entrySet()) {
			httpGet.addHeader(header.getKey(), header.getValue());
		}
		HttpResponse response = httpClient.execute(httpGet);
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	@Test
	public void test2() throws ClientProtocolException, IOException {
		String url = "http://www.mafengwo.cn/xc/15284/3.html";
		HttpClient httpClient = HttpUtil.getHttpClient();
		HttpGet httpGet = new HttpGet(url);

		Map<String, String> headers = new HashMap<>();
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Accept-Language", "zh-CN,zh;q=0.8");
		headers.put("Host", "www.mafengwo.cn");
		headers.put("Referer", "http://www.mafengwo.cn");
		headers.put("Proxy-Connection", "keep-alive");
		headers.put("Upgrade-Insecure-Requests", "1");
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
		for (Map.Entry<String, String> header : headers.entrySet()) {
			httpGet.addHeader(header.getKey(), header.getValue());
		}
		HttpResponse response = httpClient.execute(httpGet);
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

	CountDownLatch countDownLatch = new CountDownLatch(4);

	@Test
	public void test3() throws InterruptedException {
		ThreadGroup tg = new ThreadGroup("my_thread_group_1");
		ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10,
				new DefaultThreadFactory(tg));
		threadPool.submit(new Thread(tg, new MyThread49()));
		threadPool.submit(new Thread(tg, new MyThread49()));
		threadPool.submit(new Thread(tg, new MyThread49()));
		threadPool.submit(new Thread(tg, new MyThread49()));
		Thread[] threads = new Thread[4];
		tg.enumerate(threads);
		for (Thread thread : threads) {
			System.out.println(thread.getName());
			thread.join(5000);
		}
		countDownLatch.await();
	}

	public class MyThread49 implements Runnable {
		public void run() {
			System.out.println(Thread.currentThread().getThreadGroup().getName());
			try {
				while (!Thread.currentThread().isInterrupted()) {
					// System.out.println("ThreadName = " +
					// Thread.currentThread().getName());
					Thread.sleep(3000);
					countDownLatch.countDown();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static class DefaultThreadFactory implements ThreadFactory {
		private static final AtomicInteger poolNumber = new AtomicInteger(1);
		private ThreadGroup group = null;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;

		DefaultThreadFactory(ThreadGroup group) {
			SecurityManager s = System.getSecurityManager();
			this.group = group;
			namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
		}

		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
			if (t.isDaemon())
				t.setDaemon(false);
			if (t.getPriority() != Thread.NORM_PRIORITY)
				t.setPriority(Thread.NORM_PRIORITY);
			return t;
		}
	}

}
