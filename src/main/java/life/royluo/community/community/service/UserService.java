package life.royluo.community.community.service;

import life.royluo.community.community.Mapper.UserMapper;
import life.royluo.community.community.dto.GithubUser;
import life.royluo.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Roy2020-3-8
 *
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    //GitHub用户登录判断
    public String createOrUpdate(GithubUser githubUser){
        //在本地数据库查到user
        User user = new User();
        User userdb = userMapper.findByAccountId(githubUser.getId());
        String token = UUID.randomUUID().toString();
        if (userdb == null){
            //第一次登录UUID生成随机值，方便后续验证使用

            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            //新用户入库
            userMapper.insert(user);
            System.out.println("登录成功");

        }else {
            user = userdb;
            //更新本地数据User
            user.setAvatarUrl(githubUser.getAvatar_url());
            user.setGmtModified(System.currentTimeMillis());
            user.setName(githubUser.getName());
            user.setToken(token);
            userMapper.update(user);
        }
        return user.getToken();
    }

}
