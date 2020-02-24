package life.royluo.community.community.provider;

import com.alibaba.fastjson.JSON;
import life.royluo.community.community.dto.AccessTokenDTO;
import life.royluo.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
/**
 * GitHub登录
 * 2020.2.21 Roy
 */
@Component
public class GithubProvider {

    //获取AccessToken
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            System.out.println(string);
            System.out.println(token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //使用accessToken请求https://api.github.com/user?access_token=accessToken获取user
    public GithubUser getUser(String accessToken){
        System.out.println("accessToken获取user："+accessToken);
        //OkHttpClient是第三方OkHttp的用于请求的
        OkHttpClient client = new OkHttpClient();
        //使用accessToken请求返回GithubUser
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println("user"+string);
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {

        }
        return null;
    }

}
