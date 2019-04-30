package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    /**
     * 查询首页导航条中的所有分类数据的方法
     * @return List<Category>
     */
    @Override
    public List<Category> findAll() {
        /*对查询所有分类数据进行优化，进行redis查询*/
        //1.获取jedis对象
        Jedis jedis = JedisUtil.getJedis();
        //2.第一次redis数据库中查询
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        //3.判断redis中是否存在categorys
        List<Category> cs=null;
        if (categorys==null||categorys.size()==0) {
            System.out.println("从mysql数据库中查询。。。");
            //4.第一次查询redis数据库中不存在该数据，则从mysql数据库中去查询
             cs= categoryDao.findAllCategory();
             //5.将查询出来的数据存入redis数据库中
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        } else  {
            //6.第二次查询，从redis查询
            System.out.println("从redis数据库中查询。。。");
            //7.查询到的数据为Set格式，需要将数据转换为List格式
            cs=new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                cs.add(category);
            }
        }
        return cs;
    }
}
