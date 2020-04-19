package demo.community.controller;

import demo.community.dto.CommentDTO;
import demo.community.dto.QuestionDTO;
import demo.community.enums.CommentTypeEnum;
import demo.community.mapper.QuestionMapper;
import demo.community.service.CommentService;
import demo.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        model.addAttribute("relateQuestions",relatedQuestions);
        List<CommentDTO> comments = commentService.list(id, CommentTypeEnum.QUESTION);
        model.addAttribute("comments",comments);
        questionService.incViewCount(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
