package life.royluo.community.community.advice;


import life.royluo.community.community.dto.ResultDTO;
import life.royluo.community.community.exception.CustomizeErrorCode;
import life.royluo.community.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Roy20200410
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    Object handle(HttpServletRequest request, Throwable e, Model model) {
        HttpStatus status = getStatus(request);
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            //返回JSON
            if (e instanceof CustomizeException){
                System.out.println("报错："+status.value()+"-地址"+request.getContextPath());
                return ResultDTO.errprOf((CustomizeException)e);
            }else {
               return ResultDTO.errprOf(CustomizeErrorCode.SYS_ERROR);
            }
        }else {
            //错误页面跳转
            if (e instanceof CustomizeException){
                model.addAttribute("message","报错："+e.getMessage());
            }else {
                model.addAttribute("message","报错："+status.value()+CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }

    }

    //原本错误
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }


}
