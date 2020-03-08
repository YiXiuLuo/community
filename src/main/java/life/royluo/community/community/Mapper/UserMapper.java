package life.royluo.community.community.Mapper;

import life.royluo.community.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * 用户data
 * 2020.2.21 Roy
 */
@Repository
@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_Id,token,gmt_create,gmt_modified,avatar_url) " +
            "values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId")String accountId);

    @Update("UPDATE USER t SET t.TOKEN = '#{token}' WHERE t.ID = #{id}")
    void updateToken(String accountId,String token);

    @Update("UPDATE USER  SET TOKEN=#{token},name=#{name},avatar_url=#{avatarUrl},gmt_modified=#{gmtModified} WHERE ID = #{id}")
    void update(User user);
}
