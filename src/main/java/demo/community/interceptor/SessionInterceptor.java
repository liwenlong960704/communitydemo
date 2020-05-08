package demo.community.interceptor;

import demo.community.mapper.UserMapper;
import demo.community.model.User;
import demo.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length != 0){
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("token")){
                        String token = cookie.getValue();
                        User user = userMapper.findByToken(token);
                        if(user != null){
                            HttpSession session = request.getSession();
                            session.setAttribute("user",user);
                            Long unreadCount = notificationService.unreadCount(user.getId());
                            session.setAttribute("unreadCount",unreadCount);
                        }
                        break;
                    }
                }
            }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
