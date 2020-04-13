package demo.community.service;

import demo.community.dto.PaginationDTO;
import demo.community.dto.QuestionDTO;
import demo.community.mapper.QuestionMapper;
import demo.community.mapper.UserMapper;
import demo.community.model.Question;
import demo.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;


    public PaginationDTO list(Integer page, Integer size){

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer questionCount = questionMapper.countQuestions();
        paginationDTO.setPagination(questionCount, page, size);

        if(page < 1){
            page = 1;
        }
        if(page > paginationDTO.getPageCount()){
            page = paginationDTO.getPageCount();
        }

        Integer offset = size*(page - 1);

        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for(Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }
}
