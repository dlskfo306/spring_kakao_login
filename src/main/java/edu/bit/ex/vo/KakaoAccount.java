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
public class KakaoAccount {
    public Boolean profile_needs_agreement;
    public Profile profile;
    /*
    public Boolean email_needs_agreement;
    public Boolean is_email_valid;
    public Boolean is_email_verified;
    public String email;
    public Boolean age_range_needs_agreement;
    public String age_range;
    public Boolean birthday_needs_agreement;
    public String birthday;
    public Boolean gender_needs_agreement;
    public String gender;
    */
}
