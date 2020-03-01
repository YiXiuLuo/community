package life.royluo.community.community.dto;

import lombok.Data;

/**
 * GitHub三方登录请求返回的参数
 * 2020.2.21 Roy
 */
@Data
public class AccessTokenDTO {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
