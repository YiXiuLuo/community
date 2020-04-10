package life.royluo.community.community.service;

import life.royluo.community.community.Mapper.QuestionExtMapper;
import life.royluo.community.community.Mapper.QuestionMapper;
import life.royluo.community.community.Mapper.UserMapper;
import life.royluo.community.community.dto.PaginationDTO;
import life.royluo.community.community.dto.QuestionDTO;
import life.royluo.community.community.exception.CustomizeErrorCode;
import life.royluo.community.community.exception.CustomizeException;
import life.royluo.community.community.model.Question;
import life.royluo.community.community.model.QuestionExample;
import life.royluo.community.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    /**
     * 查询所有问题
     * @return
     * @param page
     * @param size
     */
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        //获取总行数
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        //实现显示翻页按钮返回总行数offset
        Integer offset = paginationDTO.setPagination(totalCount,page,size);
        //查询问题库分页
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        //装入questionDTOList
        List<QuestionDTO> questionDTOList = new ArrayList();
        for(Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        //获取总行数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);

        //实现显示翻页按钮返回总行数offset
        Integer offset = paginationDTO.setPagination(totalCount,page,size);
        //查询问题库分页数据
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, size));
        //装入questionDTOList
        List<QuestionDTO> questionDTOList = new ArrayList();
        User user = userMapper.selectByPrimaryKey(userId);
        for(Question question : questions){
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils.copyProperties工具类，将question复制到questionDTO
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;



    }

    //相关问题
    public QuestionDTO getById(Long id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null){
            throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        //封装复制QuestionDTO
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(userMapper.selectByPrimaryKey(question.getCreator()));

        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        //判断是新增还是更新
        if (question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        }else {
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updated != 1){
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    /**
     * 累计阅读数
     * @param id
     */
    public void inView(Long id) {
        Question updateQuestion = new Question();
        updateQuestion.setId(id);
        updateQuestion.setViewCount(1);
        questionExtMapper.incView(updateQuestion);
    }
}
