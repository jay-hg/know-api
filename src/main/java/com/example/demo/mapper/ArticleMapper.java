package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.entity.Article;
import com.example.demo.model.vo.ArticleVo;
import com.example.demo.model.vo.UserRankVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    @Select("SELECT a.uid, a.agree_count, u.nickname, u.avatar\n" +
            "FROM t_article a\n" +
            "JOIN t_user u ON a.uid = u.id\n" +
            "ORDER BY a.agree_count DESC\n" +
            "LIMIT 10")
    List<UserRankVo> listTopArticles();
}
