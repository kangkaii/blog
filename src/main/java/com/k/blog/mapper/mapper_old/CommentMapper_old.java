package com.k.blog.mapper.mapper_old;import com.k.blog.model.vo.CommentVo;import com.k.blog.model.vo.CommentVoExample;import org.apache.ibatis.annotations.*;import org.apache.ibatis.jdbc.SQL;import java.util.List;/** * @author kangkai on 2018/4/25. */public interface CommentMapper_old {    @Select("select * from t_comments limit #{pageNow} , #{limit}")    List<CommentVo> getComments(@Param("pageNow") int pageNow, @Param("limit") int limit);    //CommentVoExample 当做参数 写入sql    @SelectProvider(type = CommentMapperImpl.class, method = "getSQLByExampleWithBLOBs")    List<CommentVo> selectByExampleWithBLOBs(CommentVoExample example);    @SelectProvider(type = CommentMapperImpl.class, method = "getSQL4count")    Long countByExample(CommentVoExample example);    class CommentMapperImpl {        public String getSQL4count(CommentVoExample example) {            String sql4query = getSQLByExampleWithBLOBs(example);            String result = sql4query.replace("*","count(*)");            return result;        }        //办法1：本接口写方法，再调用        public String getSQLByExampleWithBLOBs(CommentVoExample example) {            return new SQL(){{                if (null == example) {                    SELECT("*");                    FROM("t_comments");                }                else {                    if (example.isDistinct()) {                        SELECT_DISTINCT(" * ");                    }else {                        SELECT("*");                    }                    FROM(" t_comments ");                    WHERE(" 1 = 1 ");                    if (null != example.getOrderByClause()) {                        ORDER_BY(example.getOrderByClause());                    }                }            }}.toString();        }    }}