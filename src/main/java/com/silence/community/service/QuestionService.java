package com.silence.community.service;

import com.silence.community.dto.PaginationDTO;
import com.silence.community.dto.QuestionDTO;
import com.silence.community.mapper.QuestionMapper;
import com.silence.community.mapper.UserMapper;
import com.silence.community.model.Question;
import com.silence.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;


    public PaginationDTO list(Integer page, Integer size) {

//        根据页码page计算偏移量offset
        Integer offset=size*(page-1);

        List<Question> questions=questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();

        PaginationDTO paginationDTO = new PaginationDTO();
        for(Question question:questions){
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestionDTOList(questionDTOList);

        Integer totalCount = questionMapper.count();

        paginationDTO.setPagination(totalCount,page,size);
        return paginationDTO;
    }
}
