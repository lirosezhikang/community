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

        Integer totalPage;
//      获取数据库中的问题总数
        Integer totalCount = questionMapper.count();

//      判断页码号
        if(totalCount%size==0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size+1;
        }

//      判断页码号
        if(page<1){
            page=1;
        }

        if(page>totalPage){
            page=totalPage;
        }


        //      根据总数，页码号，单页容量给PaginationDTO赋值
        paginationDTO.setPagination(totalPage,page);

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

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;

//      根据userId获取数据库中的问题总数
        Integer totalCount = questionMapper.countByUserId(userId);

        if(totalCount%size==0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size+1;
        }

//      判断页码号
        if(page<1){
            page=1;
        }

        if(page>totalPage){
            page=totalPage;
        }


//      根据总数，页码号，单页容量给PaginationDTO赋值
        paginationDTO.setPagination(totalPage,page);
//      根据页码page计算偏移量offset
        Integer offset=size*(page-1);

        List<Question> questions=questionMapper.listByUserId(userId,offset,size);
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


    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.getById(id);
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}
