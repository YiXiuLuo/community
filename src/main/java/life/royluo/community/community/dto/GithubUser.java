package life.royluo.community.community.dto;

import lombok.Data;

/**
 * GitHub通过AccessToken返回用户的参数
 * 2020.2.21 Roy
 * @Data自动配上get和set
 */
@Data
public class GithubUser {
    private String name;
    private String id;
    private String bio;
    //头像url
    private String avatar_url;

    @Override
    public String
    toString() {
        return "GithubUser{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
