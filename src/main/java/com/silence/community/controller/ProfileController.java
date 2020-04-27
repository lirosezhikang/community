package com.silence.community.controller;

import com.silence.community.dto.PaginationDTO;
import com.silence.community.model.User;
import com.silence.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

//  根据路径的不同展示不同的profile页面
    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "2") Integer size,
                          Model model){
        User user=(User) request.getSession().getAttribute("user");

        if(user==null){
            return "redirect:/";
        }

        //根据路径不同，给model赋不同的属性，使前端显示不同的页面
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

        PaginationDTO paginationDTO=questionService.list(user.getId(),page,size);
        model.addAttribute("paginationDTO",paginationDTO);
        return "profile";
    }

}
