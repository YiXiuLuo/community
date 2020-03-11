package life.royluo.community.community.controller;

import life.royluo.community.community.dto.QuestionDTO;
import life.royluo.community.community.model.Question;
import life.royluo.community.community.model.User;
import life.royluo.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Roy 2020.02.24
 */
@Controller
public class PublishController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String publish(@PathVariable(name = "id")Integer id,
                          Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",id);

        return "publish";
    }

    @GetMapping("/publish")
    public String publish(HttpServletRequest request,Model model){
        //判断拦截器函数是否登录成功
        User user = (User) request.getSession().getAttribute("user");
        //以防外界未登录直接请求
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "redirect:/";
        }
        return "publish";
    }

    /**
     *
     * @param title 标题
     * @param description 问题详情
     * @param tag 标签
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/publish")
    public String dopublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Integer id,
            HttpServletRequest request,
            Model model){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title==null||title.trim().equals("")) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description==null||description.trim().equals("")) {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }if (tag==null||tag.trim().equals("")) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        //判断拦截器函数是否登录成功
        User user = (User) request.getSession().getAttribute("user");
        //以防外界未登录直接请求
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        //问题赋值入库
        Question question = new Question();
        question.setTag(tag);
        question.setDescription(description);
        question.setTitle(title);
        question.setCreator(user.getId());

        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }




}
