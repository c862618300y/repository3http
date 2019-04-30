package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    /**
     * 通过sid，在tab_seller表中查询Seller对象
     * @param sid
     * @return Seller
     */
    Seller findSeller(int sid);
}
