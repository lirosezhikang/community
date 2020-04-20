package com.silence.community.mapper;

import com.silence.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user_table (account_id, name, token, gmt_create, gmt_modified) VALUES (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insertUser(User user);

    @Select("select * from user_table where token=#{token}")
    User findByToken(@Param("token") String token);
}
