package life.royluo.community.community.controller;

import life.royluo.community.community.Mapper.UserMapper;
import life.royluo.community.community.dto.PaginationDTO;
import life.royluo.community.community.model.User;
import life.royluo.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 个人页面
 * Roy2020-03-06
 */
@Controller
public class ProfileController {


    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          HttpServletRequest request,
                          Model model){


        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","关于我的评论");
        }
        //判断拦截器函数是否登录成功
        User user = (User) request.getSession().getAttribute("user");
        //以防外界未登录直接请求
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "redirect:/";
        }

        PaginationDTO paginations = questionService.list(user.getId(), page, size);
        model.addAttribute("paginations",paginations);

        return "profile";
    }



}
