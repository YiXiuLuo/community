package life.royluo.community.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"您找的问题不存在"),
    TARGET_PARAM_NOT_FOUND(2002,"该问题当前可能已被删除已无法进行评论"),
    NO_LOGIN(2003,"未登录不能进行该操作，请先登录"),
    SYS_ERROR(2004,"服务器异常或请求有误"),
    TYPE_PARAM_ERROR(2005,"评论不存在"),
    COMMENT_NOT_FOUND(2006,"您找的评论已不存在或被删除"),
    ;

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {

        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
