package com.example.demo.controller;

import com.example.demo.model.entity.Comment;
import com.example.demo.model.vo.CommentVo;
import com.example.demo.model.vo.UserRankVo;
import com.example.demo.service.ArticleService;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/rank")
public class RankController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public List<UserRankVo> listTopArticles() {
        return articleService.listTopArticles();
    }
}
