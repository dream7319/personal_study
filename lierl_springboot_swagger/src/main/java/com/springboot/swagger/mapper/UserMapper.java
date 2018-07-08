package com.springboot.swagger.mapper;

import com.springboot.swagger.base.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Delete({
        "delete from t_user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into t_user (id, name, ",
        "password, email, ",
        "sex, create_time, ",
        "update_time, has_confirmed)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, ",
        "#{sex,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{hasConfirmed,jdbcType=BIT})"
    })
    int insert(User record);

    int insertSelective(User record);

    @Select({
        "select",
        "id, name, password, email, sex, create_time, update_time, has_confirmed",
        "from t_user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.springboot.swagger.mapper.UserMapper.BaseResultMap")
    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    @Update({
        "update t_user",
        "set name = #{name,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "sex = #{sex,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "has_confirmed = #{hasConfirmed,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(User record);

    List<User> selectByCondition(@Param("name") String name, @Param("email") String email, @Param("hasConfirm") boolean hasConfirm);

    @Select({
            "select",
            "id, name, password, email, sex, create_time, update_time, has_confirmed",
            "from t_user"
    })
    @ResultMap("com.springboot.swagger.mapper.UserMapper.BaseResultMap")
    List<User> findAll();
}
