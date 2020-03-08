package life.royluo.community.community.Mapper;

import life.royluo.community.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户问题库data
 * 2020.2.24 Roy
 */
@Repository
@Mapper
public interface QuestionMapper {

    /**
     *
      * @return
     * @param offset
     * @param size
     */
    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset,@Param(value = "size")  Integer size);

    /**
     * 发布问题
     * @param question
     */
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select count(1) from question")
    Integer count();

    /**
     * 分页查找对于用户的question
     * @param userId
     * @param offset
     * @param size
     * @return
     */
    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> listByUserId(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size")  Integer size);

    /**
     * 查对于用户的question总行
     * @param userId
     * @return
     */
    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param(value = "userId") Integer userId);

    /**
     *
     * @param id
     * @return
     */
    @Select("select * from question where id = #{id}")
    Question getById(@Param(value = "id") Integer id);
}
