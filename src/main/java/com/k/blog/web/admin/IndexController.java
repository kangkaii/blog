package com.k.blog.web.admin;import com.k.blog.constant.WebConst;import com.k.blog.enums.UserAction;import com.k.blog.exception.TipException;import com.k.blog.model.bo.RestResponseBo;import com.k.blog.model.bo.StatisticsBo;import com.k.blog.model.vo.CommentVo;import com.k.blog.model.vo.ContentVo;import com.k.blog.model.vo.LogVo;import com.k.blog.model.vo.UserVo;import com.k.blog.service.LogService;import com.k.blog.service.SiteService;import com.k.blog.service.UserService;import com.k.blog.utils.GsonUtils;import com.k.blog.utils.TaleUtils;import com.k.blog.web.BaseController;import org.apache.commons.lang3.StringUtils;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.*;import javax.servlet.http.HttpServletRequest;import javax.servlet.http.HttpSession;import java.util.List;/** * @author kangkai on 2018/4/24. */@Controller@RequestMapping({"/admin"})public class IndexController extends BaseController{    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);    @Autowired    SiteService siteService;    @Autowired    LogService logService;    @Autowired    UserService userService;    /**    * Description:登录跳转到后台首页接口    * @param request    * @return 目标页面    */    @GetMapping(value = {"","/index"})    public String index(HttpServletRequest request){        //取数据        List<CommentVo> comments = siteService.recentComments(5);        List<ContentVo> contents = siteService.recentContents(5);        StatisticsBo statistics = siteService.getStatistics();        // 取最新的20条日志        List<LogVo> logs = logService.getLogs(1, 5);        //存入 request        request.setAttribute("comments", comments);        request.setAttribute("articles", contents);        request.setAttribute("statistics", statistics);        request.setAttribute("logs", logs);        return "admin/index";    }    /**     * Description:后台首页 - 个人设置     * 跳转页面接口     * @return 目标页面     */    @GetMapping(value = "profile")    public String profile() {        return "admin/profile";    }    /**     * Description:后台首页 - 个人设置     * 保存个人设置 - post请求     * @param screenName 用户展示名     * @param email email     * @param request + session     * @return RestResponseBo     */    @PostMapping(value = "profile")    @ResponseBody    public RestResponseBo saveProfile(@RequestParam String screenName, @RequestParam String email, HttpServletRequest request, HttpSession session) {        UserVo userVo = super.getUserFromRequest(request);        //校验入参        if (StringUtils.isBlank(screenName) || StringUtils.isBlank(email)) {            return RestResponseBo.fail("用户名或邮箱为空");        }        //update user        UserVo userVo4update = new UserVo(userVo.getUid(),screenName,email);        userService.updateUser4frofile(userVo4update);        //存入日志        logService.insertLog(UserAction.UPDATE_INFO.getAction(), GsonUtils.toJsonString(userVo4update),userVo.getUid(), request.getRemoteAddr());        //更改当前session        UserVo original= (UserVo)session.getAttribute(WebConst.LOGIN_SESSION_KEY);        original.setScreenName(screenName).setEmail(email);        session.setAttribute(WebConst.LOGIN_SESSION_KEY,original);        return RestResponseBo.ok();    }    @PostMapping(value = "password")    @ResponseBody    public RestResponseBo updatePwd(@RequestParam String oldPassword, @RequestParam String password,@RequestParam String repass, HttpServletRequest request, HttpSession session) {        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(password) || StringUtils.isBlank(repass)) {            return RestResponseBo.fail("三者都不能为空");        }        if (!password.equals(repass)) {            return RestResponseBo.fail("两次输入密码不一致");        }        if (oldPassword.equals(password)) {            return RestResponseBo.fail("新旧密码不能相同");        }        //session 拿到user        UserVo userVo = (UserVo)session.getAttribute(WebConst.LOGIN_SESSION_KEY);        UserVo user4update = userVo.setPassword(password);        try {            userService.check4updatePwd(userVo.getUsername(),oldPassword);            userService.updateUser4password(user4update);            //存入日志            logService.insertLog(UserAction.UPDATE_PWD.getAction(), GsonUtils.toJsonString(user4update),userVo.getUid(), request.getRemoteAddr());            //更改当前session            UserVo original= (UserVo)session.getAttribute(WebConst.LOGIN_SESSION_KEY);            original.setPassword(password);            session.setAttribute(WebConst.LOGIN_SESSION_KEY,original);            return RestResponseBo.ok();        }catch (Exception e) {            String msg = "密码修改失败";            if (e instanceof TipException) {                //return RestResponseBo.fail(msg+TaleUtils.getPart(e.toString(),TipException.class.getName()));                return RestResponseBo.fail(msg + e.toString());            }else {                return RestResponseBo.fail(msg+":后台报错");            }        }    }}