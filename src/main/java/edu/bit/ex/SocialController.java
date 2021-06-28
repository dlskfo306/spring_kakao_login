package edu.bit.ex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.bit.ex.service.KakaoService;
import edu.bit.ex.vo.KakaoAuth;
import edu.bit.ex.vo.KakaoProfile;

/**
 * 네이티브 앱 키 d0802d111306e7b3104dcbbafe10c0d7 REST API 키
 * 74ac062503f83a3d8797eb9fab8f5150 JavaScript 키
 * c968061f0d5fdd879a3abdb3c953c709 Admin 키 1bce400298ab0a20ad90c4ac25f19c20
 */
@Controller

//request
// GET /oauth/authorize?client_id={REST_API_KEY}&redirect_uri={REDIRECT_URI}&response_type=code HTTP/1.1
//Host: kauth.kakao.com

// 토큰 요청
/*
 * POST /oauth/token HTTP/1.1 Host: kauth.kakao.com Content-type:
 * application/x-www-form-urlencoded;charset=utf-8
 */

public class SocialController {
    @Autowired
    private KakaoService kakaoService;

    @GetMapping("/social")
    public String social(Model model) {
        System.out.println("social() .. ");
        model.addAttribute("kakaoUrl", kakaoService.getAuthoriationUrl());

        return "social/login";
    }

    @GetMapping("/auth/kakao/callback")
    public String social1(String code, Model model) {
        //첫번째 단계. 인증코드 받기
        System.out.println("code : " + code);

        //두번째 단계. 인증코드 받아서 넘기기
        KakaoAuth kakaoAuth = kakaoService.getKakaoTokenInfo(code);
        
        //세번째 프로필 받아오기 
        KakaoProfile profile = kakaoService.getKakaoProfile(kakaoAuth.getAccess_token());
        
        model.addAttribute("user",profile);
        
        return "social/social_home";

    }

}
