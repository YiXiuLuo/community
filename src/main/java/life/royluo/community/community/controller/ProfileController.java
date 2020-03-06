package life.royluo.community.community.controller;

import life.royluo.community.community.Mapper.UserMapper;
import life.royluo.community.community.model.User;
import life.royluo.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 个人页面
 * Roy2020-03-06
 */
@Controller
public class ProfileController {


    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          HttpServletRequest request,
                          Model model){


        if ("questions".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","关于我的评论");
        }
        //判断是否登录过
        User user = null;
        //获取浏览器的cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                //找到cookie中的token
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //去数据库查找是否又对应token
                    user = userMapper.findByToken(token);
                    //user不为null时候将用户数据放入session
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        //以防外界未登录直接请求
        if (user == null){
            model.addAttribute("error","用户未登录");
            return "redirect:/";
        }

        questionService.list(user.getId(),page,size);

        return "profile";
    }



}
