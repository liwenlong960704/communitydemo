package demo.community.controller;

import demo.community.model.User;
import demo.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

//    @GetMapping("/notification/{id}")
//    public String profile(@PathVariable(value = "id") Long id,
//                          HttpServletRequest request){
//        User user = (User)request.getSession().getAttribute("user");
//        if(user == null){
//            return "redirect:/";
//        }
//
//
//
//    }

}
