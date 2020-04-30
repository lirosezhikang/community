package com.silence.community.mapper;

import com.silence.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{size} offset #{offset}")
    List<Question> list(Integer offset, Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator=#{userId} limit #{size} offset #{offset}")
    List<Question> listByUserId(Integer userId, Integer offset, Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id=#{id}")
    Question getById(@Param("id") Integer id);

    @Select("update question set title=#{title},description=#{description},tag=#{tag},gmt_modified=#{gmtModified} where id=#{id}")
    void update(Question question);
}
