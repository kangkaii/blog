package com.k.blog.mapper;import com.k.blog.model.vo.LogVo;import org.apache.ibatis.annotations.Insert;/** * @author kangkai on 2018/4/23. */public interface LogMapper {    @Insert("insert into t_logs (action,data,author_id,ip,created) values (#{action},#{data},#{authorId},#{ip},#{created})")    int addLog(LogVo logVo);}