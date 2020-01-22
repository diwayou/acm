package com.diwayou.spring.dao;

import com.diwayou.spring.domain.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("select * from user")
    List<User> getAllUsers();

    @Select("select * from user where id=#{id}")
    User getById(Integer id);

    @Insert({"insert into user(name) values (#{name})"})
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", before = false, resultType = Integer.class)
    int insert(User user);

    @Update("update user set name=#{name} where id=#{id}")
    int update(User user);

    @Delete("delete from user where id=#{id}")
    int delete(Integer id);
}
