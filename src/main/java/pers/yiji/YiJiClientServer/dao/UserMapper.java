package pers.yiji.YiJiClientServer.dao;


import pers.yiji.YiJiClientServer.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM ACCT_USER")
    List<User> findAll();

    @Select("SELECT * FROM ACCT_USER WHERE NAME = #{name}")
    User findByName(@Param("name") String name);

    @Insert("INSERT INTO ACCT_USER(NAME, AGE, PWD) VALUES(#{name}, #{age}, #{pwd})")
    int insert(@Param("name") String name, @Param("pwd") String pwd, @Param("age") Integer age);

}