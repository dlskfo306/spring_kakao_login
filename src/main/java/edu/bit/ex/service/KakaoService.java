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




//REST API 키    74ac062503f83a3d8797eb9fab8f5150
//Redirect URI  http://localhost:8282/ex/auth/kakao/callback

//request URL
//https://kauth.kakao.com/oauth/authorize?client_id={REST_API_KEY}&redirect_uri={REDIRECT_URI}&response_type=code HTTP/1.1

//코드
//http://localhost:8282/ex/auth/kakao/callback?code=kZHRTq20GSzNeUrX7t8P_JPMYtvjjmgdh7vENQMo1I53Q0Yf4Fc-H-mF2a8UWdeC8ViMGwo9dRsAAAF8bhBaYw


@Service
public class KakaoService {
    
    //rest api키
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
    
    
  //토큰받기
    //"https://kauth.kakao.com/oauth/token" 
    /*
       POST /oauth/token HTTP/1.1
       Host: kauth.kakao.com
       Content-type: application/x-www-form-urlencoded;charset=utf-8
     */
    
    //request
    /*
    curl -v -X POST "https://kauth.kakao.com/oauth/token" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "grant_type=authorization_code" \
    -d "client_id={REST_API_KEY}" \
    --data-urlencode "redirect_uri={REDIRECT_URI}" \
    -d "code={AUTHORIZE_CODE}"
    */
    
    //response:성공
    /*
     HTTP/1.1 200 OK
    Content-Type: application/json;charset=UTF-8
    {
        "token_type":"bearer",
        "access_token":"{ACCESS_TOKEN}",
        "expires_in":43199,
        "refresh_token":"{REFRESH_TOKEN}",
        "refresh_token_expires_in":25184000,
        "scope":"account_email profile"
    }
     */
    private final static String K_TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    
    public KakaoAuth getKakaoTokenInfo(String code) {
        
        // http 요청을 간단하게 해줄 수 있는 클래스
        //Retrofit -> 안드로이드 앱.   
        RestTemplate restTemplate = new RestTemplate();
        
        //헤더구성 클래스(Set Header) / HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        
        //파라미터 넘기기(Set Parameter) / HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", K_CLIENT_ID);
        params.add("redirect_uri", K_REDIRECT_URI);
        params.add("code", code);
        
        //set http entity / HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
        
        //실제 요청하기
        //Http 요청하기 - POST 요청을 보내고 결과로 ResponseEntity로 반환 받는다.
        ResponseEntity<String> response = restTemplate.postForEntity(K_TOKEN_URI, kakaoTokenRequest, String.class);
        
        //response에서 .getBody()와 .getHeaders()로 HTTP 메세지를 나눠서 확인할 수 있다.
        System.out.println("getBody() : " + response.getBody());
        System.out.println("getHeaders() : " + response.getHeaders());
        
        //http://www.jsonschema2pojo.org/
        //json->자바코드로
       
        //gson 라이브러리를 이용해서 Http 통신 결과가 200 OK 일 때
        //response.getBody()의 JSON 데이터를 KakaoAuth.class에 담는다.
        Gson gson = new Gson();
        if (response.getStatusCode() == HttpStatus.OK) {
            
            return gson.fromJson(response.getBody(), KakaoAuth.class);
        }

        return null;
    }
    
    
    //프로필 받아오기
    /*
    curl -v -X GET "https://kapi.kakao.com/v2/user/me" \
    -H "Authorization: Bearer {ACCESS_TOKEN}" //Header를 이렇게 만들어라
    */
    
    
    private final static String K_PROFILE_URI = "https://kapi.kakao.com/v2/user/me";
    
    public KakaoProfile getKakaoProfile(String accessToken) {
        
        //http 요청을 간단하게 해줄 수 있는 클래스
        RestTemplate restTemplate = new RestTemplate();
        
        //헤더구성 클래스(Set Header) / HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
         
        //set http entity / HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        
        //실제 요청하기
        //Http 요청하기 - POST 요청을 보내고 결과로 ResponseEntity로 반환 받는다.
        ResponseEntity<String> response = restTemplate.postForEntity(K_PROFILE_URI, request, String.class);
        
        System.out.println("getBody() : " + response.getBody());
        
        //gson 라이브러리를 이용해서 Http 통신 결과가 200 OK 일 때
        //response.getBody()의 JSON 데이터를 KakaoAuth.class에 담는다.
        Gson gson = new Gson();
        if(response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), KakaoProfile.class);
        }
        
        return null;
    }
    
    
}
