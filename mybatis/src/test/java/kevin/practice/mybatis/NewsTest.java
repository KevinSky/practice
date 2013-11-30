package kevin.practice.mybatis;

<<<<<<< HEAD
import kevin.practice.mybatis.service.impl.NewsServiceImpl;
=======
import java.sql.SQLException;

import kevin.lib.util.exceptions.BusinessException;
import kevin.lib.util.exceptions.ServiceException;
import kevin.practice.mybatis.service.NewsService;
>>>>>>> 9b1e22d932124bcf0b387e19e3ce8f55aca735c1

public class NewsTest {

    public static void testTransaction() {
        String host = "google";
        String url = "http://www.google.com";
        String title = "index";
        try {
            NewsService.getNewsService().saveNewsWithTransaction(host, title, url);
        } catch (BusinessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
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
