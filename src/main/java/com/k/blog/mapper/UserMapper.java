package com.k.blog.mapper;import com.k.blog.entity.User;import org.apache.ibatis.annotations.Param;import org.apache.ibatis.annotations.Select;import java.util.List;/** * @author kangkai on 2018/4/17. */public interface UserMapper {    @Select("SELECT * FROM test_user WHERE id = #{id}")    List<User> findById(@Param("id") long id);    @Select("SELECT * FROM test_user WHERE name like concat(concat('%',#{name}),'%')")    User findByName(@Param("name") String name);}