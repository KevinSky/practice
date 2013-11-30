package kevin.practice.mybatis;

import kevin.practice.mybatis.service.impl.NewsServiceImpl;

public class NewsTest {

	public static void testSave() {

		String host = "www.goole.com";
		String url = "http://www.google.com";
		String title = "index";

		try {
			NewsServiceImpl.getNewsService().saveNews(host, url, title);
			System.out.println("save!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testSaveTransaction() {
		String host = "www.goole.com";
		String url = "http://www.google.com";
		String title = "index";

		try {
			NewsServiceImpl.getNewsService().saveNewsWithTransaction(host, url,
					title);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		testSaveTransaction();
	}
}
