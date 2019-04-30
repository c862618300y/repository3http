package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//分类表
@WebServlet("/category/*")
public class CateporyServlet extends BaseServlet {
    //声明UserService业务对象共用
    private CategoryService service = new CategoryServiceImpl();

    /**
     * 查询首页导航条中的所有分类数据的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用service中的findAll方法，完成首页导航条中的所有分类的查询
        List<Category> categories = service.findAll();
        //2.直接将传入的对象序列化为json，并且写回客户端
        writeValue(response,categories);
    }
}
