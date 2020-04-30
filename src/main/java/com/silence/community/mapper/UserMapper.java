package com.silence.community.mapper;

import com.silence.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user_table (account_id, name, token, gmt_create, gmt_modified,avatar_url) VALUES (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl})")
    void insertUser(User user);

    @Select("select * from user_table where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user_table where id=#{id}")
    User findById(@Param("id") Integer creator);

    @Select("select * from user_table where account_id=#{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Select("update user_table set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where id=#{id}")
    void update(User user);
}
