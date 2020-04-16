package demo.community.service;

import demo.community.dto.PaginationDTO;
import demo.community.dto.QuestionDTO;
import demo.community.exception.CustomizeErrorCode;
import demo.community.exception.CustomizeException;
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

        Integer pageCount;

        if(questionCount%size == 0){
            pageCount = questionCount/size;
        }else{
            pageCount = questionCount/size + 1;
        }

        if(page < 1){
            page = 1;
        }
        if(page > pageCount){
            page = pageCount;
        }

        paginationDTO.setPagination(pageCount,page);

        Integer offset = Math.max(size*(page - 1),0);


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

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer questionCount = questionMapper.countQuestionsByUserId(userId);

        Integer pageCount;

        if(questionCount%size == 0){
            pageCount = questionCount/size;
        }else{
            pageCount = questionCount/size + 1;
        }

        if(page < 1){
            page = 1;
        }
        if(page > pageCount){
            page = pageCount;
        }

        paginationDTO.setPagination(pageCount,page);

        Integer offset = Math.max(size*(page - 1),0);

        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        User user = userMapper.findById(userId);

        for(Question question : questions){
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(questionDTO.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.createQuestion(question);
        }else{
            question.setGmtModified(System.currentTimeMillis());
            boolean successful = questionMapper.updateQuestion(question);
            if(!successful){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }

    }
}
