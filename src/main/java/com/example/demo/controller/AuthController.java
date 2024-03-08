package com.example.demo.controller;

import com.example.demo.model.vo.AuthVo;
import com.example.demo.service.UserService;
import com.example.demo.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author andy
 * @date 2016/12/16
 */
@RequestMapping("/auth")
@RestController
@Slf4j
public class AuthController {
    @Autowired
    private UserService userService;

    @RequestMapping("/wx-login")
    public Object wxLogin(@RequestBody AuthVo authVo) {
        return userService.loginOrRegiste(authVo);
    }
}