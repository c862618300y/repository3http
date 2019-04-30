package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface RouteService {
    /**
     * 查询页面上线路数据和分页条数据的方法
     *
     * @return PageBean
     */
    public PageBean findPageBean(HttpServletRequest request) throws UnsupportedEncodingException;

    /**
     *根据rid查询一个旅游线路的详细信息
     * @param request
     * @return
     */
    Route routeDetails(HttpServletRequest request);
}
