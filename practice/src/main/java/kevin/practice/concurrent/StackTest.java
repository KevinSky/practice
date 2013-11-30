package kevin.practice.concurrent;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.HttpException;

import kevin.lib.util.http.HttpClientConfig;
import kevin.lib.util.http.HttpClientManagerUtil;

public class StackTest {

	
	public static void main(String[] args) {
		ThreadPoolExecutor t = new ThreadPoolExecutor(3,4,10,TimeUnit.SECONDS, new LinkedBlockingDeque(1000));
		t.submit(new ThreadWhile());
		t.submit(new ThreadSleep());
		t.submit(new ThreadRun());
	
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static class ThreadWhile implements Runnable{
		@Override 
		public void run() {
			HttpClientConfig config = new HttpClientConfig();
	        config.setSocketTimeOut(1500);
	        config.setConnectionTimeout(700);
	        config.setConnectionManagerTimeout(1000);
	        config.setMaxTotalConnections(2000);
	        config.setMaxConnectionsPerHost(1000);
	        HttpClientManagerUtil httpClientManagerUtil = new HttpClientManagerUtil(config);
	        
			while(true) {
				try {
					System.out.println(httpClientManagerUtil.doGet("http://app.yyembed.yy.com/0001/liveList?from=from&appId=appId&appVer=appVer&ver=ver&tabId=100&start=0&count=20&sortType=1"));
				} catch (SocketTimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static class ThreadSleep implements Runnable{
		@Override
		public void run() {
			try {
				Thread.sleep(Long.MAX_VALUE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static class ThreadRun implements Runnable {
		@Override
		public void run() {
			System.out.println("over");
		}
	}
}
