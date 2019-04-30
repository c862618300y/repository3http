package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();


    /**
     * 查询页面上线路数据和分页条数据的方法
     *
     * @return PageBean
     */
    public PageBean findPageBean(HttpServletRequest request) throws UnsupportedEncodingException {
        //1.获取cid、currentPage、pageSize
        String cidStr = request.getParameter("cid");//编号
        String crrentPageStr = request.getParameter("currentPage");//当前页码
        String pageSizeStr = request.getParameter("pageSize");//每页显示数据的条数
        //接收线路名称
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        //2.处理参数(类型转换和安全判断)
        //2.1处理cidStr
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }
        //2.2处理crrentPageStr，需做安全判断
        int crrentPage = 0;
        if (crrentPageStr != null && crrentPageStr.length() > 0) {
            crrentPage = Integer.parseInt(crrentPageStr);
        } else {
            crrentPage = 1;
        }
        //2.3pageSizeStr，需做安全判断
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }

        //3.创建PageBean对象
        PageBean pb = new PageBean();
        //给pb crrentPage变量设置值
        pb.setCurrentPage(crrentPage);
        //System.out.println(pb.getCurrentPage());
        //给pb pageSize变量设置值
        pb.setPageSize(pageSize);

        //4.调用dao中的findTotalCount(cid)方法查询总记录数
        int totalCount = routeDao.findTotalCount(cid, rname);
        //给pb totalCount变量设置值
        pb.setTotalCount(totalCount);

        //5.调用dao中的findTotalCount(cid)方法查询总记录数
        //求出开始索引:(当前页码-1)*每页显示的数据条数
        int strat = (crrentPage - 1) * pageSize;
        List<Route> routes = routeDao.findList(cid, strat, pageSize, rname);
        //给pb list变量设置值
        pb.setList(routes);

        //6.求总页数:totalCount%pageSize==0 ? totalCount/pageSize : (totalCount/pageSize)+1
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        //给pb totalPage变量设置值
        pb.setTotalPage(totalPage);

        //7.返回设置完的PageBean对象
        return pb;
    }

    /**
     * 根据rid查询一个旅游线路的详细信息
     *
     * @param request
     * @return route
     */
    @Override
    public Route routeDetails(HttpServletRequest request) {
        //1.获取线路id
        String rid = request.getParameter("rid");
        //2.通过rid,在route表中查询route对象，并返回
        Route route = routeDao.findRoute(Integer.parseInt(rid));
        //3.通过route表和routeImg表的共同编号rid，查询出route对象的变量List<RouteImg>，并重新设置变量值
        List<RouteImg> routeImgList = routeImgDao.findRouteImgList(route.getRid());
        route.setRouteImgList(routeImgList);
        //4.通过route表和seller表的共同编号sid，查询出route对象的变量Seller，并重新设置变量值
        Seller seller = sellerDao.findSeller(route.getSid());
        route.setSeller(seller);

        //5.查询收藏次数
        int count=favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }
}
