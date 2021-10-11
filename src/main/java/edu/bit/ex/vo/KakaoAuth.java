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
public class KakaoAuth {
    public String token_type;
    public String access_token;
    public Integer expires_in;
    public String refresh_token;
    public Integer refresh_token_expires_in;
    public String scope;
}
