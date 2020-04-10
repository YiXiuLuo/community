package life.royluo.community.community.controller;

import life.royluo.community.community.Mapper.UserMapper;
import life.royluo.community.community.dto.AccessTokenDTO;
import life.royluo.community.community.dto.GithubUser;
import life.royluo.community.community.model.User;
import life.royluo.community.community.provider.GithubProvider;
import life.royluo.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;
    /**
     *GitHub登录使用
     *github返回code和state
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request, HttpServletResponse response){
        //获取配置文件值
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(clientSecret);

        //获取
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //通过accessToken请求GitHub返回GithubUser
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println("githubUser="+githubUser==null);
        //判断GitHub是否登录成功
        if (githubUser !=null && githubUser.getId() != null){
            String token =userService.createOrUpdate(githubUser);
            //登录成功，写cookie
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            System.out.println("登录登录失败");
            //登录失败，重新登录
            return "redirect:/";
        }

    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/loginOut")
    public String loginOut(HttpServletRequest request,
                           HttpServletResponse response){
        //删除session里的user
        request.getSession().removeAttribute("user");
        //使用cookie重新复制token为null达到删除的效果
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);


        return "redirect:/";
    }








}
