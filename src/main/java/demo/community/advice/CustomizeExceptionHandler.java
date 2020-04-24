package demo.community.advice;

import com.alibaba.fastjson.JSON;
import demo.community.dto.ResultDTO;
import demo.community.exception.CustomizeErrorCode;
import demo.community.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
@Slf4j
public class CustomizeExceptionHandler {

    @ExceptionHandler
    ModelAndView     handler(Throwable e, Model model,
                   HttpServletRequest request,
                   HttpServletResponse response) {
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            //返回json
            ResultDTO resultDTO;
            if(e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            }else{
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }catch (IOException ioe){

            }
            return null;

        }else{
            //错误页面跳转
            if(e instanceof CustomizeException){
                model.addAttribute("message", e.getMessage());
            }else{
                log.error("handle error",e);
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
