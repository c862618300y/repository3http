package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据cid查询每种分类的总记录数
     *
     * @param cid
     * @param rname
     * @return int
     */
    public int findTotalCount(int cid, String rname) {
        int totalCount = 0;
        try {
            //String sql = "select count(*) from tab_route where and cid = ? and rname like ? ";
            //定义sql模板
            String sql = "select count(*) from tab_route where 1=1 ";
            //用于拼接sql
            StringBuilder sb = new StringBuilder(sql);
            List params = new ArrayList<>();//存储所有的条件值
            //判断cid是否有值
            if (cid != 0) {
                sb.append(" and cid = ? ");
                params.add(cid);//添加 ? 对应的值
            }
            if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
                sb.append(" and rname like ? ");
                params.add("%" + rname + "%");
            }
            sql = sb.toString();
            totalCount = template.queryForObject(sql, Integer.class, params.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return totalCount;
    }

    /**
     * 根据cid查询每种分类的每页数据集合
     *
     * @return List<Route>
     */
    public List<Route> findList(int cid, int strat, int pageSize, String rname) {
        List<Route> routes = null;
        try {
            //String sql = "select * from tab_route where and cid = ? and rname like ?  limit ?,?";
            //定义sql模板
            String sql = "select * from tab_route where 1=1 ";
            //用于拼接sql
            StringBuilder sb = new StringBuilder(sql);
            List params = new ArrayList<>();//存储所有的条件值
            //判断cid是否有值
            if (cid != 0) {
                sb.append(" and cid = ? ");
                params.add(cid);//添加 ? 对应的值
            }
            if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
                sb.append(" and rname like ? ");
                params.add("%" + rname + "%");
            }
            sb.append(" limit ? , ?");//分页条件
            sql = sb.toString();
            params.add(strat);
            params.add(pageSize);
            routes = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return routes;
    }

    @Override
    public Route findRoute(int rid) {
        Route route = null;
        try {
            String sql = "select * from tab_route where rid=?";
            route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return route;
    }
}
