package com.k.blog.web;import com.github.pagehelper.PageInfo;import com.k.blog.entity.User;import com.k.blog.mapper.UserMapper;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.ResponseBody;import java.util.List;/** * @author kangkai on 2018/4/17. */@Controller@RequestMapping("/admin")public class UserController {    @Autowired    private UserMapper userMapper;    @GetMapping(value = "/login")    public String login() {        return "admin/login";    }    private static final Logger logger = LoggerFactory.getLogger(UserController.class);    @RequestMapping("/getUserById")    @ResponseBody    public PageInfo<User> getUserById(long id) {        List<User> userList = userMapper.findById(id);        PageInfo<User> userPageInfo = new PageInfo<>(userList);        logger.debug("success");        logger.info("success");        logger.trace("success");        logger.error("success");        logger.warn("success");        return userPageInfo;    }    @GetMapping(value = "/hello")    public String helloGet() {        return "hello";    }}