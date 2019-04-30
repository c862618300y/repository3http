package cn.itcast.travel.service;

import javax.servlet.http.HttpServletRequest;

public interface FavoriteService {
    /**
     * 判断用户是否收藏该线路的功能
     *
     * @param request
     * @return
     */
    boolean isFavorite(HttpServletRequest request);

    /**
     * 用户添加收藏线路的方法
     * @param request
     */
    void addFavorite(HttpServletRequest request);
}
