package kevin.practice.mybatis.test1.database.gen.mapper;

import java.util.List;
import kevin.practice.mybatis.test1.database.gen.model.Test1;
import kevin.practice.mybatis.test1.database.gen.model.Test1Example;
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

public interface Test1Mapper {
    @SelectProvider(type=Test1SqlProvider.class, method="countByExample")
    int countByExample(Test1Example example);

    @DeleteProvider(type=Test1SqlProvider.class, method="deleteByExample")
    int deleteByExample(Test1Example example);

    @Delete({
        "delete from test1",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into test1 (id, name)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR})"
    })
    int insert(Test1 record);

    @InsertProvider(type=Test1SqlProvider.class, method="insertSelective")
    int insertSelective(Test1 record);

    @SelectProvider(type=Test1SqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR)
    })
    List<Test1> selectByExample(Test1Example example);

    @Select({
        "select",
        "id, name",
        "from test1",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR)
    })
    Test1 selectByPrimaryKey(Integer id);

    @UpdateProvider(type=Test1SqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Test1 record, @Param("example") Test1Example example);

    @UpdateProvider(type=Test1SqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Test1 record, @Param("example") Test1Example example);

    @UpdateProvider(type=Test1SqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Test1 record);

    @Update({
        "update test1",
        "set name = #{name,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Test1 record);
}