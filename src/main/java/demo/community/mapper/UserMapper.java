package demo.community.mapper;

import demo.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name, account_id, token, gmt_create, gmt_modified, avatar_url, bio) " +
            "values(#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl}, #{bio})")
    boolean insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Long id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") Long accountId);

    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, bio = #{bio}, " +
            "avatar_url = #{avatarUrl} where id = #{id}")
    boolean update(User user);
}
