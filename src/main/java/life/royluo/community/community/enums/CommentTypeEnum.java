package life.royluo.community.community.enums;

/**
 * Roy20200410
 */
public enum CommentTypeEnum {
    //回复问题
    QUESTION(1),
    //回复评论
    COMMENT(2);

    private Integer type;

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum typeEnum : CommentTypeEnum.values()) {
            if (typeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
