package kevin.practice.mybatis.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kevin.lib.util.JsonUtil;
import kevin.lib.util.SpringContextUtil;
import kevin.practice.mybatis.dao.model.News;
import kevin.practice.mybatis.service.NormalTestService;
import kevin.practice.mybatis.service.impl.NewsServiceImpl;

public class TestServlet extends HttpServlet {

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
            String name1 = req.getParameter("name1");
            String name2 = req.getParameter("name2");
            NormalTestService service = SpringContextUtil.getBean("testService");
            service.saveTwoTest(name1, name2);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            code = -99999;
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
