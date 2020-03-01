package life.royluo.community.community.model;

import lombok.Data;

/**
 * 用户
 * 2020.2.21 Roy
 */
@Data
public class User {

    private Integer id;
    private String name;
    private String accountId;
    //由UUID生成，登录验证使用的
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    //头像url
    private String avatarUrl;

}
