package life.royluo.community.community.model;

import lombok.Data;


@Data public class Quesstion {

    private Integer id;
    //标题
    private String title;
    //问题详情
    private String description;
    //标签
    private String tag;

    private Long gmtCreate;

    private Long gmtModified;
    //用户ID
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;

}
