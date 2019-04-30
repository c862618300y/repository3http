package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//用户表
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    //声明UserService业务对象共用
    private UserService service = new UserServiceImpl();
    /**
     * 用户注册的功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void registUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.创建UserService对象
       // UserService service = new UserServiceImpl();
        //2.调用checkUser(request,response)方法完成验证码的校验
        //boolean flag = service.checkCode(request, response);
        boolean flag = checkCode(request, response);
        //判断这个标记
        if (flag) {
            return;
        }
        //3.调用service中的方registUser(request)方法，来完成注册页面的校验
        ResultInfo ri = service.registUser(request);
        //4.直接将传入的对象序列化为json，并且写回客户端
        writeValue(response,ri);
    }

    /**
     * 用户激活的功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void activeUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //UserService service=new UserServiceImpl();
        //调用service中的active()方法，来完成用户账号的激活
        service.active(request,response);
    }

    /**
     * 用户登录的功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.创建UserService对象
        //UserService service = new UserServiceImpl();
        //2.调用checkUser(request,response)方法完成验证码的校验
        boolean flag = checkCode(request, response);
        //判断这个标记
        if(flag){
            return;
        }
        //3.调用service中的方loginUser(request)方法，来完成登录页面的校验
        ResultInfo ri = service.loginUser(request);
        //4.回响flag的状态和异常信息给客服端
        writeValue(response,ri);
    }

    /**
     * 登录成功后，在首页展示用户名的功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.从session域中取出user对象
        Object user = request.getSession().getAttribute("user");
        //2.将user对象回写给客服端
        writeValue(response,user);
    }

    /**
     * 用户退出的功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exitUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.销毁session域
        request.getSession().invalidate();
        //2.再重定向到登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
}
