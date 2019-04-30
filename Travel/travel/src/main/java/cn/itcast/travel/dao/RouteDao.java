package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 根据cid查询每种分类的总记录数
     *
     * @param cid
     * @param rname
     * @return int
     */
    public int findTotalCount(int cid, String rname);

    /**
     * 根据cid查询每种分类的每页数据集合
     *
     * @return List<Route>
     */
    public List<Route> findList(int cid, int strat, int pageSize, String rname);

    /**
     * 通过rid,在tab_route表中查询Route对象，并返回
     * @param rid
     * @return Route
     */
    Route findRoute(int rid);
}
