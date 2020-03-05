package life.royluo.community.community.controller;

import life.royluo.community.community.Mapper.QuestionMapper;
import life.royluo.community.community.Mapper.UserMapper;
import life.royluo.community.community.dto.QuestionDTO;
import life.royluo.community.community.model.User;
import life.royluo.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 2020.2.21 Roy
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    /**
     * 主页面
     * @param request
     * @return
     * 2020.2.22 Roy
     */
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {

        //判断是否是历史登录用户
        if (request.getCookies() != null) {
            //获取浏览器的cookies
            Cookie[] cookies = request.getCookies();
            //找到cookie中的token
            for (Cookie cookie : cookies) {
                //找到cookie中的token
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //去数据库查找是否又对应token
                    User user = userMapper.findByToken(token);
                    //user不为null时候将用户数据放入session
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }


        List<QuestionDTO> questionDTOList = questionService.list();
        model.addAttribute("questionDTOList",questionDTOList);
        return "index";

    }


}
