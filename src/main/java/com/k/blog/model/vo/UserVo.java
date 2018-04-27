package com.k.blog.model.vo;

import java.io.Serializable;

/**
 * @author 
 */
public class UserVo implements Serializable {
    /**
     * user表主键
     */
    private Integer uid;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户的邮箱
     */
    private String email;

    /**
     * 用户的主页
     */
    private String homeUrl;

    /**
     * 用户显示的名称
     */
    private String screenName;

    /**
     * 用户注册时的GMT unix时间戳
     */
    private Integer created;

    /**
     * 最后活动时间
     */
    private Integer activated;

    /**
     * 上次登录最后活跃时间
     */
    private Integer logged;

    /**
     * 用户组
     */
    private String groupName;

    private static UserVo userVo = new UserVo();

    public UserVo() {}

    public UserVo(Integer uid, String screenName,String email) {
        this.uid = uid;
        this.screenName = screenName;
        this.email = email;
    }

    public Integer getUid() {
        return uid;
    }

    public UserVo setUid(Integer uid) {
        this.uid = uid;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserVo setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserVo setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserVo setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public UserVo setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
        return this;
    }

    public String getScreenName() {
        return screenName;
    }

    public UserVo setScreenName(String screenName) {
        this.screenName = screenName;
        return this;
    }

    public Integer getCreated() {
        return created;
    }

    public UserVo setCreated(Integer created) {
        this.created = created;
        return this;
    }

    public Integer getActivated() {
        return activated;
    }

    public UserVo setActivated(Integer activated) {
        this.activated = activated;
        return this;
    }

    public Integer getLogged() {
        return logged;
    }

    public UserVo setLogged(Integer logged) {
        this.logged = logged;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public UserVo setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public UserVo getUserVo() {
        return userVo;
    }

    public UserVo setUserVo(UserVo userVo) {
        this.userVo = userVo;
        return this;
    }
}