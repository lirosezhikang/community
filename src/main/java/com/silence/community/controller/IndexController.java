package com.silence.community.controller;

import com.silence.community.dto.PaginationDTO;
import com.silence.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

//  根据页码page和页面容量size显示index页面
    @GetMapping("/")
    public String index(@RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        Model model){

        //根据page和size获得Pagination对象，并传入model中，用于前端页面的展示
        PaginationDTO paginationDTO=questionService.list(page,size);
        model.addAttribute("paginationDTO",paginationDTO);
        return "index";
    }
}
