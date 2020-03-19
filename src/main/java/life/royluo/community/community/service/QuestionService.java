package life.royluo.community.community.service;

import life.royluo.community.community.Mapper.QuestionMapper;
import life.royluo.community.community.Mapper.UserMapper;
import life.royluo.community.community.dto.PaginationDTO;
import life.royluo.community.community.dto.QuestionDTO;
import life.royluo.community.community.model.Question;
import life.royluo.community.community.model.User;
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


    /**
     * 查询所有问题
     * @return
     * @param page
     * @param size
     */
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        //获取总行数
        Integer totalCount = questionMapper.count();
        //实现显示翻页按钮返回总行数offset
        Integer offset = paginationDTO.setPagination(totalCount,page,size);
        //查询问题库分页
        List<Question> questions = questionMapper.list(offset, size);

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

    public PaginationDTO list(Integer userId, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        //获取总行数
        Integer totalCount = questionMapper.countByUserId(userId);
        //实现显示翻页按钮返回总行数offset
        Integer offset = paginationDTO.setPagination(totalCount,page,size);
        //查询问题库分页数据
        List<Question> questions = questionMapper.listByUserId(userId,offset, size);

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
    public QuestionDTO getById(Integer id) {

        Question question = questionMapper.getById(id);
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
            questionMapper.create(question);
        }else {
            //更新
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
