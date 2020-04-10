package life.royluo.community.community.controller;

import life.royluo.community.community.Mapper.CommentMapper;
import life.royluo.community.community.dto.CommentDTO;
import life.royluo.community.community.dto.ResultDTO;
import life.royluo.community.community.exception.CustomizeErrorCode;
import life.royluo.community.community.model.Comment;
import life.royluo.community.community.model.User;
import life.royluo.community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Roy20200410
 * 问题评论回复
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    /**
     *
     * @param commentDTO JSON数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errprOf(CustomizeErrorCode.NO_LOGIN);
        }


        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(1L);
        comment.setLikeCount(0L);
        commentService.insert(comment);

        return ResultDTO.okOf();
    }



}
