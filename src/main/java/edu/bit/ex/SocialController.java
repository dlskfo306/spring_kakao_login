package edu.bit.ex;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.bit.ex.service.KakaoService;
import edu.bit.ex.vo.KakaoAuth;
import edu.bit.ex.vo.KakaoProfile;
import lombok.extern.log4j.Log4j;


//REST API 키    74ac062503f83a3d8797eb9fab8f5150
//Redirect URI  http://localhost:8282/ex/auth/kakao/callback

//request URL
//https://kauth.kakao.com/oauth/authorize?client_id={REST_API_KEY}&redirect_uri={REDIRECT_URI}&response_type=code HTTP/1.1

//코드
//http://localhost:8282/ex/auth/kakao/callback?code=kZHRTq20GSzNeUrX7t8P_JPMYtvjjmgdh7vENQMo1I53Q0Yf4Fc-H-mF2a8UWdeC8ViMGwo9dRsAAAF8bhBaYw


//토큰 요청
/*
* POST /oauth/token HTTP/1.1 Host: kauth.kakao.com Content-type:
* application/x-www-form-urlencoded;charset=utf-8
*/
@Log4j
@Controller
public class SocialController {
	
	@Autowired
	private KakaoService kakaoService;
    
    @GetMapping("/social")
    public String social(Model model) {
        log.info("social()..");
        model.addAttribute("kakaoUrl", kakaoService.getAuthoriationUrl());
        
        return"social/login";
    }
    
    

    @GetMapping("/auth/kakao/callback")
    public String social1(String code, Model model) {
        
        //1.인증코드 받기
        System.out.println("code : " + code);
        
        //2.인증코드 받아서 넘기기
        KakaoAuth kakaoAuth = kakaoService.getKakaoTokenInfo(code);
        
        //3.프로필 받아오기
        KakaoProfile profile = kakaoService.getKakaoProfile(kakaoAuth.getAccess_token());
        
        model.addAttribute("user", profile);
        
        
        return "social/social_home";
    }
	
}
