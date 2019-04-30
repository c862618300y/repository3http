package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 通过username查询用户名
     * @param username
     * @return user
     */
    User findByUsername(String username);

    /**
     * 保存用户信息的方法，向数据库中添加一条用户信息的数据
     * @param user
     */
    void save(User user);

    /**
     * 根据code查询数据库该用户是否存在
     * @param code
     * @return User
     */
    User findByactiveCode(String code);

    /**
     * 修改激活状态的方法，已完成用户激活的功能
     * @param user
     */
    void updateStatus(User user);

    /**
     * 通过用户名和密码查询用户信息
     * @param username
     * @param password
     * @return User
     */
    User findByUsernameAndPassword(String username, String password);
}
