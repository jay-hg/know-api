package com.example.demo.model.vo;

import com.example.demo.model.entity.Article;
import com.example.demo.model.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRankVo {

    private String uid;

    //点赞数
    @JsonProperty(value = "score")
    private Integer agreeCount;

    @JsonProperty(value = "name")
    private String nickname;

    //image id
    private String avatar;
}
