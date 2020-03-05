package life.royluo.community.community.service;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import life.royluo.community.community.Mapper.QuestionMapper;
import life.royluo.community.community.Mapper.UserMapper;
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
     */
    public List<QuestionDTO> list() {
        //查询所有问题库
        List<Question> questions = questionMapper.list();
        //装入questionDTOList
        List<QuestionDTO> questionDTOList = new ArrayList();
        for(Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }


        return questionDTOList;
    }
}
