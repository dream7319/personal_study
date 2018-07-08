package com.springboot.swagger.mapper;

import com.springboot.swagger.base.Spring4all;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface Spring4allMapper {
    @Delete({
        "delete from spring4all",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into spring4all (id, nickname, ",
        "title, link, category, ",
        "comment, like_num, ",
        "read_num, recommended, ",
        "create_time)",
        "values (#{id,jdbcType=INTEGER}, #{nickname,jdbcType=VARCHAR}, ",
        "#{title,jdbcType=VARCHAR}, #{link,jdbcType=VARCHAR}, #{category,jdbcType=VARCHAR}, ",
        "#{comment,jdbcType=INTEGER}, #{likeNum,jdbcType=INTEGER}, ",
        "#{readNum,jdbcType=INTEGER}, #{recommended,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(Spring4all record);

    @InsertProvider(type=Spring4allSqlProvider.class, method="insertSelective")
    int insertSelective(Spring4all record);

    @Select({
        "select",
        "id, nickname, title, link, category, comment, like_num, read_num, recommended, ",
        "create_time",
        "from spring4all",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="nickname", property="nickname", jdbcType=JdbcType.VARCHAR),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="link", property="link", jdbcType=JdbcType.VARCHAR),
        @Result(column="category", property="category", jdbcType=JdbcType.VARCHAR),
        @Result(column="comment", property="comment", jdbcType=JdbcType.INTEGER),
        @Result(column="like_num", property="likeNum", jdbcType=JdbcType.INTEGER),
        @Result(column="read_num", property="readNum", jdbcType=JdbcType.INTEGER),
        @Result(column="recommended", property="recommended", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Spring4all selectByPrimaryKey(Integer id);

    @UpdateProvider(type=Spring4allSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Spring4all record);

    @Update({
        "update spring4all",
        "set nickname = #{nickname,jdbcType=VARCHAR},",
          "title = #{title,jdbcType=VARCHAR},",
          "link = #{link,jdbcType=VARCHAR},",
          "category = #{category,jdbcType=VARCHAR},",
          "comment = #{comment,jdbcType=INTEGER},",
          "like_num = #{likeNum,jdbcType=INTEGER},",
          "read_num = #{readNum,jdbcType=INTEGER},",
          "recommended = #{recommended,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Spring4all record);
}
