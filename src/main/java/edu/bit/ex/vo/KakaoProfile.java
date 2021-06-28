package edu.bit.ex.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoProfile {
    
    public Integer id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;
    
}
