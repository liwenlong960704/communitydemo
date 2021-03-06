package demo.community.controller;

import demo.community.cache.TagCache;
import demo.community.exception.CustomizeErrorCode;
import demo.community.exception.CustomizeException;
import demo.community.mapper.QuestionMapper;
import demo.community.model.Question;
import demo.community.model.User;
import demo.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;


    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required = false) String tag,
                            @RequestParam(value = "id", required = false) Long id,
                            HttpServletRequest request,
                            Model model){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.get());

        if(title == null || "".equals(title)){
            model.addAttribute("error","标题不能为空!");
            return "publish";
        }
        if(description == null || "".equals(description)){
            model.addAttribute("error","问题补充不能为空！");
            return "publish";
        }
        if(tag == null || "".equals(tag)){
            model.addAttribute("error","标签不能为空！");
            return "publish";
        }

        User user = (User)request.getSession().getAttribute("user");

        if(user == null){
            model.addAttribute("error","用户未登录！");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);

        questionService.createOrUpdate(question);

        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model){
        Question question = questionMapper.getById(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        model.addAttribute("id",question.getId());
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("tags", TagCache.get());

        return "publish";
    }

}
