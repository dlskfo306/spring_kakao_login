package edu.bit.ex.service;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import edu.bit.ex.vo.KakaoAuth;
import edu.bit.ex.vo.KakaoProfile;

/**
네이티브 앱 키   dc75133bcec7f5dcd194fbdefd15201c
REST API 키   4c2fd3e1c94fe470cd22f0a9ab93a84a
JavaScript 키   1db8ad520418901c76f4d9f17302d0a1
Admin 키   e27daabdde88445ede171a9e7c59d939
 */
@Service
public class KakaoService {
   
   private final static String K_CLIENT_ID="74ac062503f83a3d8797eb9fab8f5150";
   private final static String K_REDIRECT_URI="http://localhost:8282/ex/auth/kakao/callback";
   
   // requst 요청
   //GET /oauth/authorize?client_id={REST_API_KEY}&redirect_uri={REDIRECT_URI}&response_type=code HTTP/1.1
   //Host: kauth.kakao.com
   
   public String getAuthoriationUrl() {
       String kakaoUrl = "https://kauth.kakao.com/oauth/authorize?" + "client_id=" + K_CLIENT_ID + "&redirect_uri="
                  + K_REDIRECT_URI + "&response_type=code";
      return kakaoUrl;
   }
   
   
   //"https://kauth.kakao.com/oauth/token" 
   //POST /oauth/token HTTP/1.1
   //Host: kauth.kakao.com
   //Content-type: application/x-www-form-urlencoded;charset=utf-8
   /*
   curl -v -X POST "https://kauth.kakao.com/oauth/token" \
    -d "grant_type=authorization_code" \
    -d "client_id={REST_API_KEY}" \
    -d "redirect_uri={REDIRECT_URI}" \
    -d "code={AUTHORIZATION_CODE}"
   */
   
   //HTTP/1.1 200 OK
   //-Type: application/json;charset=UTF-8
   //{
   //    "token_type":"bearer",
   //    "access_token":"{ACCESS_TOKEN}",
   //    "expires_in":43199,
   //    "refresh_token":"{REFRESH_TOKEN}",
   //    "refresh_token_expires_in":25184000,
   //    "scope":"account_email profile"
   //}
   private final static String K_TOKEN_URI ="https://kauth.kakao.com/oauth/token"; 
   
   public KakaoAuth getKakaoTokenInfo(String code) {
      
      // http 요청을 간단하게 해줄 수 있는 클래스
      //Retrofit -> 안드로이드 앱.      
      RestTemplate restTemplate = new RestTemplate();
      
      //헤더구성 클래스(Set Header)
      HttpHeaders headers = new HttpHeaders(); 
      headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
      
      //파라미터 넘기기(Set Parameter)
      MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
      params.add("grant_type", "authorization_code");
      params.add("client_id", K_CLIENT_ID);
      params.add("redirect_uri", K_REDIRECT_URI);
      params.add("code", code);
      
      // Set http entity
      HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
      
      //실제 요청하기
      //Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답을 받음.
      ResponseEntity<String> response = restTemplate.postForEntity(K_TOKEN_URI, kakaoTokenRequest, String.class);
      
      System.out.println("getBody() : " + response.getBody());
      System.out.println("getHeaders() : " + response.getHeaders());
      
      //http://www.jsonschema2pojo.org/
      //json->자바코드로
     
      //Gson, Json Simple, ObjectMapper 세가지 정도의 클래스가 있음
      Gson gson = new Gson();
      if (response.getStatusCode() == HttpStatus.OK) {
          
          return gson.fromJson(response.getBody(), KakaoAuth.class);
      }

      return null;
      
   }
   
   
   //프로필 받아오기
   //curl -v -X GET "https://kapi.kakao.com/v2/user/me" \
   //-H "Authorization: Bearer {ACCESS_TOKEN}" //Header를 이렇게 만들어라
   private final static String K_PROFILE_URI = "https://kapi.kakao.com/v2/user/me";
   
   public KakaoProfile getKakaoProfile(String accessToken) {
       
       // http 요청을 간단하게 해줄 수 있는 클래스   
       RestTemplate restTemplate = new RestTemplate();
       
       //헤더구성 클래스(Set Header)
       HttpHeaders headers = new HttpHeaders(); 
       headers.set("Authorization", "Bearer " + accessToken);
       
       // Set http entity
       HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
       
       //실제 요청하기
       //Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답을 받음.
       ResponseEntity<String> response = restTemplate.postForEntity(K_PROFILE_URI, request, String.class);
       
       System.out.println("getBody() : " + response.getBody());
       
       
       //http://www.jsonschema2pojo.org/
       //json->자바코드로
      
       //Gson, Json Simple, ObjectMapper 세가지 정도의 클래스가 있음
       
       
       Gson gson = new Gson();
       if (response.getStatusCode() == HttpStatus.OK) {
           
           return gson.fromJson(response.getBody(), KakaoProfile.class);
       }

       return null;

   }
   
}