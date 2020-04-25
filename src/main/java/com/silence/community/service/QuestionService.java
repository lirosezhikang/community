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

//  根据page页码编号和size单页容量返回PaginationDTO
    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
//      获取数据库中的问题总数
        Integer totalCount = questionMapper.count();
//      根据总数，页码号，单页容量给PaginationDTO赋值
        paginationDTO.setPagination(totalCount,page,size);

//      判断页码号
        if(page<1){
            page=1;
        }

        if(page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }


//      根据页码page计算偏移量offset
        Integer offset=size*(page-1);

        List<Question> questions=questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();


        for(Question question:questions){
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestionDTOList(questionDTOList);




        return paginationDTO;
    }
}
