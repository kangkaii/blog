package com.k.blog.web;import com.k.blog.constant.WebConst;import com.k.blog.enums.UserAction;import com.k.blog.exception.TipException;import com.k.blog.model.bo.RestResponseBo;import com.k.blog.model.vo.UserVo;import com.k.blog.service.LogService;import com.k.blog.service.UserService;import com.k.blog.utils.TaleUtils;import org.apache.catalina.servlet4preview.http.HttpServletRequest;import org.apache.commons.lang3.StringUtils;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.*;import javax.servlet.http.HttpServletResponse;/** * @author kangkai on 2018/4/17. */@Controller@RequestMapping("/admin")public class UserController extends BaseController{    private static final Logger logger = LoggerFactory.getLogger(UserController.class);    @Autowired    LogService logService;    @Autowired    private UserService userService;    /**     * 登录页     */    @GetMapping(value = "/login")    public String login() {        return "admin/login";    }    /**     * 登录接口     * @return 服务器回传数据类(包含失败提示等信息)     */    @PostMapping(value = "login")    @ResponseBody    public RestResponseBo doLogin(String username,String password,@RequestParam(required = false) String rememberMe,                                  HttpServletRequest request,                                  HttpServletResponse response) {        //取出baseController中 缓存map        Integer count_login = cache.get("login_error_count"+username);        //有异常抛出： userService.login(username,password);        try{            UserVo userVo = userService.login(username,password);            //存session            request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, userVo);            //写入 log            //判断rememberMe 是否开启            if (StringUtils.isNotBlank(rememberMe)) {                //勾选了 记住我按钮 存cookie -->  uid 为user表主键                TaleUtils.setCookie(response, userVo.getUid());            }            //写入日志            logService.insertLog(UserAction.LOGIN.getAction(),null,userVo.getUid(),request.getRemoteAddr());        }catch (Exception e) {            //失败 缓存+1            count_login = (null == count_login) ? 1:1+count_login;            if (count_login > 3) {                return RestResponseBo.fail("输入密码错误次数超过3次，请10分钟后登陆");            }            cache.set("login_error_count"+username,count_login,10 * 60);            String msg = "登陆失败";            if (e instanceof TipException) {                return RestResponseBo.fail(msg+":"+TaleUtils.getPart(e.toString(),"TipException:"));            }else {                return RestResponseBo.fail(msg+":后台报错");            }        }        return RestResponseBo.ok();    }}