package life.royluo.community.community.dto;

import life.royluo.community.community.exception.CustomizeErrorCode;
import life.royluo.community.community.exception.CustomizeException;
import lombok.Data;

/**
 * Roy20200410
 * 返回错误信息
 */
@Data
public class ResultDTO {
    private Integer code;
    private String message;

   public static  ResultDTO errprOf(Integer code,String message){
       ResultDTO resultDTO = new ResultDTO();
       resultDTO.setCode(code);
       resultDTO.setMessage(message);
       return resultDTO;
   }

    public static ResultDTO errprOf(CustomizeErrorCode noLogin) {
        return errprOf(noLogin.getCode(),noLogin.getMessage());
    }
    public static ResultDTO errprOf(CustomizeException e) {
        return errprOf(e.getCode(),e.getMessage());
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }


}
