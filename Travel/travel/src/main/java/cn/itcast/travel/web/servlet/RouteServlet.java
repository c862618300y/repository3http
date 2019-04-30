package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import cn.itcast.travel.web.servlet.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService=new FavoriteServiceImpl();

    /**
     * 查询页面上线路数据和分页条的功能
     *
     * @return PageBean
     */
    public void findRoutePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用routeService中的findPageBean(request)方法查询页面上线路数据和分页条数据
        PageBean pageBean = routeService.findPageBean(request);
        // System.out.println(pageBean);
        //2.直接将传入的对象序列化为json，并且写回客户端
        writeValue(response, pageBean);

    }

    /**
     * 根据rid查询一个旅游线路的详细信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void routeDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1,调用routeService中的方法完成查询
        Route route = routeService.routeDetails(request);
        //3.直接将传入的对象序列化为json，并且写回客户端
        writeValue(response, route);
    }

    /**
     * 判断用户是否收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1,调用favoriteService中的方法完成查询
        boolean flag = favoriteService.isFavorite(request);
        //System.out.println(flag);
        //3.直接将传入的对象序列化为json，并且写回客户端
        writeValue(response, flag);
    }

    /**
     * 用户收藏线路的功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //调用favoriteService中的方法完成添加
        favoriteService.addFavorite(request);
    }
}

