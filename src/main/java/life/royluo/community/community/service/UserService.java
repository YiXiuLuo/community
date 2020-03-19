package life.royluo.community.community.service;

import life.royluo.community.community.Mapper.UserMapper;
import life.royluo.community.community.dto.GithubUser;
import life.royluo.community.community.model.User;
import life.royluo.community.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(githubUser.getId());
        List<User> users = userMapper.selectByExample(userExample);
        String token = UUID.randomUUID().toString();
        User user = new User();
        if (users.size() == 0){
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
            //更新本地数据User
            user.setAvatarUrl(githubUser.getAvatar_url());
            user.setGmtModified(System.currentTimeMillis());
            user.setName(githubUser.getName());
            user.setToken(token);

            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(users.get(0).getId());
            userMapper.updateByExampleSelective(user, example);
        }
        return user.getToken();
    }

}
