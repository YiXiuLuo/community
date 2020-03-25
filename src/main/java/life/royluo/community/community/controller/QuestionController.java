package life.royluo.community.community.controller;

import life.royluo.community.community.dto.QuestionDTO;
import life.royluo.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Roy2020-3-8
 *
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/question/{id}")
    public String qusetion(@PathVariable(name = "id")Integer id,
                           Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        //累计阅读数
        questionService.inView(id);
        model.addAttribute("question",questionDTO);

        return "question";
    }


}
