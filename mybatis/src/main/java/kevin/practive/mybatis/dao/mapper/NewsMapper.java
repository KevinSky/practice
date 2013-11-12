package kevin.practive.mybatis.dao.mapper;

import java.util.List;

import kevin.practive.mybatis.dao.model.News;
import kevin.practive.mybatis.dao.model.NewsExample;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface NewsMapper {
    @SelectProvider(type=NewsSqlProvider.class, method="countByExample")
    int countByExample(NewsExample example);

    @DeleteProvider(type=NewsSqlProvider.class, method="deleteByExample")
    int deleteByExample(NewsExample example);

    @Delete({
        "delete from news",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into news (id, host, ",
        "title, url)",
        "values (#{id,jdbcType=BIGINT}, #{host,jdbcType=VARCHAR}, ",
        "#{title,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR})"
    })
    int insert(News record);

    @InsertProvider(type=NewsSqlProvider.class, method="insertSelective")
    int insertSelective(News record);

    @SelectProvider(type=NewsSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="host", property="host", jdbcType=JdbcType.VARCHAR),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR)
    })
    List<News> selectByExample(NewsExample example);

    @Select({
        "select",
        "id, host, title, url",
        "from news",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="host", property="host", jdbcType=JdbcType.VARCHAR),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR)
    })
    News selectByPrimaryKey(Long id);

    @UpdateProvider(type=NewsSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") News record, @Param("example") NewsExample example);

    @UpdateProvider(type=NewsSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") News record, @Param("example") NewsExample example);

    @UpdateProvider(type=NewsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(News record);

    @Update({
        "update news",
        "set host = #{host,jdbcType=VARCHAR},",
          "title = #{title,jdbcType=VARCHAR},",
          "url = #{url,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(News record);
}