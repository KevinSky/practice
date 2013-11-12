package kevin.practice.mybatis.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kevin.lib.util.JsonUtil;
import kevin.practice.mybatis.service.NewsService;
import kevin.practive.mybatis.dao.model.News;

public class MyBatisServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -7637912166517579391L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doMethod(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doMethod(req, resp);
    }
    
    protected void doMethod(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int code = 1;
        Object data = null;
        String message = "";
        try {
            List<News> list = NewsService.getNewsService().getNewsList();
            data = list;
        } catch (Exception e) {
            code = -1;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", code);
        result.put("message", message);
        result.put("data", data);
        String json = JsonUtil.toJson(result);
        resp.setHeader("Content-type","text/json;charset=UTF-8");
        resp.getWriter().write(json);
        resp.flushBuffer();
    }
    
}
