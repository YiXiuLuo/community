package life.royluo.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 2020.2.21 Roy
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public  String index(){ return "index"; }

}
