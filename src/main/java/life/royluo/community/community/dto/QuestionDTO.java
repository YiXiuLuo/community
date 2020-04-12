package life.royluo.community.community.dto;

import life.royluo.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    //标题
    private String title;
    //问题详情
    private String description;
    //标签
    private String tag;

    private Long gmtCreate;

    private Long gmtModified;
    //用户ID
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;


}
