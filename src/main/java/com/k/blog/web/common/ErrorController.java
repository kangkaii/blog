package com.k.blog.web.common;import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.RequestMapping;/** * @author kangkai on 2018/5/10. */@Controller@RequestMapping("/error")public class ErrorController {    /**     * 404页面     */    @GetMapping(value = "/404")    public String error_404() {        return "comm/error_404";    }    /**     * 500页面     */    @GetMapping(value = "/500")    public String error_500() {        return "comm/error_500";    }}