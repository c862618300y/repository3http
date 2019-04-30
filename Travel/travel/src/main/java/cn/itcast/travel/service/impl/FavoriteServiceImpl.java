package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 判断用户是否收藏该线路的功能
     *
     * @param request
     * @throws ServletException
     * @throws IOException
     */
    public boolean isFavorite(HttpServletRequest request) {
        //1.获取线路id和用户id
        int rid = Integer.parseInt(request.getParameter("rid"));
        User user = (User) request.getSession().getAttribute("user");
        //2.判断用户是否登录
        int uid;
        if (user == null) {
            //用户没登录
            uid = 0;
        } else {
            //用户登录了，获取uid
            uid = user.getUid();
        }
        //3.根据uid和rid查询favorite表
        Favorite favorite = favoriteDao.findByUidAndRid(rid, uid);
        return favorite != null;//如果查询到该对象则返回true,否则返回false
    }

    @Override
    public void addFavorite(HttpServletRequest request) {
        //1.获取线路id和用户id
        int rid = Integer.parseInt(request.getParameter("rid"));
        User user = (User) request.getSession().getAttribute("user");
        //2.判断用户是否登录
        int uid;
        if (user == null) {
            //用户没登录
            return;
        } else {
            //用户登录了，获取uid
            uid = user.getUid();
        }
        //3.添加线路
        favoriteDao.addFavorite(rid,uid);
    }
}
