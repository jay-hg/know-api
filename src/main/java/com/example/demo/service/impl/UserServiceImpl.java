package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.mapper.ArticleMapper;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.VideoMapper;
import com.example.demo.model.entity.Article;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.Video;
import com.example.demo.model.vo.AuthVo;
import com.example.demo.model.vo.UserVo;
import com.example.demo.service.UserService;
import com.example.demo.utils.DataTimeUtil;
import com.example.demo.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private VideoMapper videoMapper;

    @Autowired
    private WxMpService wxMpService;

    @Override
    public void save(User user) throws Exception {
        if (userMapper.selectOne(new QueryWrapper<User>().in("phone", user.getPhone())) != null)
            throw new Exception("当前手机号码已注册");
        if (userMapper.selectOne(new QueryWrapper<User>().in("nickname", user.getNickname())) != null)
            throw new Exception("该昵称已存在");
        user.setCreateAt(DataTimeUtil.getNowTimeString());
        userMapper.insert(user);
    }

    @Override
    public UserVo login(User user) throws Exception {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.in("phone", user.getPhone());
        wrapper.in("password", user.getPassword());
        User one = userMapper.selectOne(wrapper);
        if (one == null) throw new Exception("账号密码错误");
        return getUserVo(one);
    }

    private UserVo getUserVo(User user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setNickname(user.getNickname());
        userVo.setAvatar(user.getAvatar());
        userVo.setSchool(user.getSchool());
        userVo.setCreateAt(user.getCreateAt());
        userVo.setArticleCount(articleMapper.selectCount(new QueryWrapper<Article>().in("uid", user.getId())));
        userVo.setVideoCount(videoMapper.selectCount(new QueryWrapper<Video>().in("uid", user.getId())));
        userVo.setCommentCount(commentMapper.selectCount(new QueryWrapper<Comment>().in("uid", user.getId())));
        return userVo;
    }

    @Override
    public UserVo loginOrRegiste(AuthVo authVo) {
        try {
            WxOAuth2UserInfo userInfo = wxMpService.getOAuth2Service().getUserInfo(wxMpService.getOAuth2Service().getAccessToken(authVo.getCode()), "zh_CN");
            User user = userMapper.selectById(userInfo.getOpenid());
            if (user == null) {
                user = new User();
                user.setId(userInfo.getOpenid());
                user.setNickname(userInfo.getNickname());
                user.setAvatar(userInfo.getHeadImgUrl());
                user.setCreateAt(DataTimeUtil.getNowTimeString());
                userMapper.insert(user);
            }
            return getUserVo(user);
        } catch (WxErrorException e) {
            log.error("error", e);
            throw new RuntimeException("微信授权登录失败");
        }
    }
}
