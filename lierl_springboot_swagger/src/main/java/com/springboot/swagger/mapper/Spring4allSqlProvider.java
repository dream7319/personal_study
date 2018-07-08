package com.springboot.swagger.mapper;

import com.springboot.swagger.base.Spring4all;
import org.apache.ibatis.jdbc.SQL;

public class Spring4allSqlProvider {

    public String insertSelective(Spring4all record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("spring4all");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getNickname() != null) {
            sql.VALUES("nickname", "#{nickname,jdbcType=VARCHAR}");
        }
        
        if (record.getTitle() != null) {
            sql.VALUES("title", "#{title,jdbcType=VARCHAR}");
        }
        
        if (record.getLink() != null) {
            sql.VALUES("link", "#{link,jdbcType=VARCHAR}");
        }
        
        if (record.getCategory() != null) {
            sql.VALUES("category", "#{category,jdbcType=VARCHAR}");
        }
        
        if (record.getComment() != null) {
            sql.VALUES("comment", "#{comment,jdbcType=INTEGER}");
        }
        
        if (record.getLikeNum() != null) {
            sql.VALUES("like_num", "#{likeNum,jdbcType=INTEGER}");
        }
        
        if (record.getReadNum() != null) {
            sql.VALUES("read_num", "#{readNum,jdbcType=INTEGER}");
        }
        
        if (record.getRecommended() != null) {
            sql.VALUES("recommended", "#{recommended,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Spring4all record) {
        SQL sql = new SQL();
        sql.UPDATE("spring4all");
        
        if (record.getNickname() != null) {
            sql.SET("nickname = #{nickname,jdbcType=VARCHAR}");
        }
        
        if (record.getTitle() != null) {
            sql.SET("title = #{title,jdbcType=VARCHAR}");
        }
        
        if (record.getLink() != null) {
            sql.SET("link = #{link,jdbcType=VARCHAR}");
        }
        
        if (record.getCategory() != null) {
            sql.SET("category = #{category,jdbcType=VARCHAR}");
        }
        
        if (record.getComment() != null) {
            sql.SET("comment = #{comment,jdbcType=INTEGER}");
        }
        
        if (record.getLikeNum() != null) {
            sql.SET("like_num = #{likeNum,jdbcType=INTEGER}");
        }
        
        if (record.getReadNum() != null) {
            sql.SET("read_num = #{readNum,jdbcType=INTEGER}");
        }
        
        if (record.getRecommended() != null) {
            sql.SET("recommended = #{recommended,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}