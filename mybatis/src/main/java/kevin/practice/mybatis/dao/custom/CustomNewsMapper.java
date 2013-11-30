package kevin.practice.mybatis.dao.custom;

import java.util.List;

import kevin.practice.mybatis.dao.mapper.NewsMapper;
import kevin.practice.mybatis.dao.model.News;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

public interface CustomNewsMapper extends NewsMapper {

    @Select({ "select", "*", "from news", "order by id desc",
            "limit #{start,jdbcType=INTEGER},#{count,jdbcType=INTEGER}" })
    @Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR) })
    List<News> getNewsList(@Param("start") int start, @Param("count") int count);

}
