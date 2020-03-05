package life.royluo.community.community.Mapper;

import life.royluo.community.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户data
 * 2020.2.24 Roy
 */
@Repository
@Mapper
public interface QuestionMapper {

    /**
     *
      * @return
     */
    @Select("select * from question")
    List<Question> list();

    /**
     * 发布问题
     * @param question
     */
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

}
