package kevin.practice.mybatis.test2.database.gen.mapper;

import java.util.List;
import kevin.practice.mybatis.test2.database.gen.model.Test2;
import kevin.practice.mybatis.test2.database.gen.model.Test2Example;
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

public interface Test2Mapper {
    @SelectProvider(type=Test2SqlProvider.class, method="countByExample")
    int countByExample(Test2Example example);

    @DeleteProvider(type=Test2SqlProvider.class, method="deleteByExample")
    int deleteByExample(Test2Example example);

    @Delete({
        "delete from test2",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into test2 (id, name)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR})"
    })
    int insert(Test2 record);

    @InsertProvider(type=Test2SqlProvider.class, method="insertSelective")
    int insertSelective(Test2 record);

    @SelectProvider(type=Test2SqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR)
    })
    List<Test2> selectByExample(Test2Example example);

    @Select({
        "select",
        "id, name",
        "from test2",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR)
    })
    Test2 selectByPrimaryKey(Integer id);

    @UpdateProvider(type=Test2SqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Test2 record, @Param("example") Test2Example example);

    @UpdateProvider(type=Test2SqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Test2 record, @Param("example") Test2Example example);

    @UpdateProvider(type=Test2SqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Test2 record);

    @Update({
        "update test2",
        "set name = #{name,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Test2 record);
}