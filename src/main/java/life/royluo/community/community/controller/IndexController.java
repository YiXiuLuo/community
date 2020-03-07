package life.royluo.community.community.controller;

import life.royluo.community.community.dto.PaginationDTO;
import life.royluo.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


/**
 * 2020.2.21 Roy
 */
@Controller
public class IndexController {


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
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size) {


        //首页问题展示
        PaginationDTO paginations = questionService.list(page, size);
        model.addAttribute("paginations",paginations);
        return "index.html";

    }


}
