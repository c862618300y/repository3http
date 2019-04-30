package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     * 通过rid,在tab_route_img表中查询出所有的RouteImg对象
     * @param rid
     * @return List<RouteImg>
     */
    List<RouteImg> findRouteImgList(int rid);
}
