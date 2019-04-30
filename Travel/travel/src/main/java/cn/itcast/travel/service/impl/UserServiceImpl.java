package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;
import org.apache.commons.beanutils.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    /**
     * 注册功能
     * @param request
     * @return
     */
    @Override
    public ResultInfo registUser(HttpServletRequest request) {
        //1.封装user对象
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //2.调用dao中的findByUsername(user.getUsername())方法，完成用户名的查询
        User registUser = userDao.findByUsername(user.getUsername());

        //3.判断registUser是否为空
        boolean flag;
        if (registUser != null) {
            //用户名存在，不可以注册
            flag = false;
        } else {
            //用户名不存在，可以注册

            //4.调用dao中的save(user)方法，保存用户信息
            //4.1设置激活码，唯一字符串
            user.setCode(UuidUtil.getUuid());
            //4.2设置激活状态
            user.setStatus("N");
            userDao.save(user);
            //4.3设置邮件内容,并激活邮件发送
            String content = "<a href='http://localhost/travel/user/activeUser?code=" + user.getCode() + "'>点击激活【黑马旅游网】账号</a>";
            MailUtils.sendMail(user.getEmail(), content, "激活注册账号");
            flag = true;
        }

        ResultInfo info = new ResultInfo();//用于设置flag状态异常信息\
        //设置flag状态异常信息
        if (flag) {
            //注册成功
            info.setFlag(true);
        } else {
            //注册失败
            //发生异常，设置flag状态为false
            info.setFlag(false);
            //设置异常信息
            info.setErrorMsg("该用户名已存在，注册失败！");
        }
        return info;
    }

    /**
     * 激活注册功能
     * @param request
     * @param response
     */
    @Override
    public void active(HttpServletRequest request, HttpServletResponse response) {
        //1.获取激活码
        String code = request.getParameter("code");
        //防止恶意访问
        if (code != null) {
            //2.调用dao中的findByactiveCode()根据激活码查询用户信息
            User user = userDao.findByactiveCode(code);

            //3.判断user是否为空
            //定义一个标记
            boolean flag;
            if (user != null) {
                //4.该用户在数据库中存在，则调用dao中的updateStatus()方法修改激活状态
                userDao.updateStatus(user);
                flag = true;
            } else {
                flag = false;
            }

            //5.定义一个标记，判断激活与否
            String activeMsg = null;
            if (flag) {
                //激活成功,跳转登录页面
                activeMsg = "激活成功。<a href='http://localhost/travel/login.html'>登录</a>";
            } else {
                //激活失败
                activeMsg = "激活失败！请联系管理员";
            }
            //6.将activeMsg写回给客服端(别忘记设置编码)
            response.setContentType("text/html;charset=utf-8");
            try {
                response.getWriter().write(activeMsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 登录功能
     * @param request
     * @return
     */
    @Override
    public ResultInfo loginUser(HttpServletRequest request) {
        //1.获取登录页面的请求参数
        Map<String, String[]> map = request.getParameterMap();
        //2.封装user对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //3.调用dao中的findByUsernameAndPassword()方法，查询用户信息
        User loginUser = userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        //4.校验登录界面的请求参数
        ResultInfo info = new ResultInfo();//用于设置flag状态异常信息\
        if (loginUser == null) {
            //用户不存在，用户名或密码输入有误！
            info.setFlag(false);
            info.setErrorMsg("用户名或密码输入有误！");

        } else if (loginUser != null && !"Y".equals(loginUser.getStatus())) {
            //校验激活码状态
            info.setFlag(false);
            info.setErrorMsg("您的账号还未激活！请先进行激活。");
        } else {
            //用户存在，用户名和密码输入正确，登录成功
            info.setFlag(true);
            //存储loginUser到session域中
            request.getSession().setAttribute("user", loginUser);
        }
        return info;
    }
}
