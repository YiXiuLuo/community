package life.royluo.community.community.controller;

import life.royluo.community.community.dto.AccessTokenDTO;
import life.royluo.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8881/callback");
        accessTokenDTO.setClient_id("2dbec06cb53028c46cc9");
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret("79c8aaa913ba3e2e97084384810f4672a9115339");

        githubProvider.getAccessToken(accessTokenDTO);

        return "index";
    }








}
