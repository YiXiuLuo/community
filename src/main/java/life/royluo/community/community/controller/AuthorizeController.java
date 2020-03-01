package life.royluo.community.community.controller;

import life.royluo.community.community.Mapper.UserMapper;
import life.royluo.community.community.dto.AccessTokenDTO;
import life.royluo.community.community.dto.GithubUser;
import life.royluo.community.community.model.User;
import life.royluo.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
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
    private UserMapper userMapper;

    /**
     *GitHub登录使用
     *github返回code和state
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request, HttpServletResponse response){
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
        //判断GitHub是否登录成功
        if (githubUser !=null && githubUser.getId() != null){
            User user = new User();
            //第一次登录UUID生成随机值，方便后续验证使用
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            System.out.println("头像Url"+githubUser.getAvatar_url());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            //新用户入库
            userMapper.insert(user);
            System.out.println("登录成功");
            //登录成功，写cookie
            response.addCookie(new Cookie("token",token));
//            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else {
            //登录失败，重新登录
            return "redirect:/";
        }

    }








}
