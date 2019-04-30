package cn.itcast.travel.service;

import cn.itcast.travel.domain.ResultInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public interface UserService {
    /**
     * 校验注册页面的方法
     * @param request
     * @return ResultInfo对象
     */
    ResultInfo registUser(HttpServletRequest request);
    /**
     * 用户账号的激活的方法
     * @param request
     * @param response
     */
    void active(HttpServletRequest request, HttpServletResponse response);

    /**
     * 校验登录页面的方法
     * @param request
     * @return ResultInfo
     */
    ResultInfo loginUser(HttpServletRequest request);
}
