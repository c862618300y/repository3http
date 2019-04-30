package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    /**
     * 根据rid和uid查询favorite表
     * @param rid
     * @param uid
     * @return
     */
    Favorite findByUidAndRid(int rid, int uid);

    /**
     * 根据rid查询线路的收藏次数
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 根据rid和uid添加线路到favorite表中
     * @param rid
     * @param uid
     */
    void addFavorite(int rid, int uid);
}
