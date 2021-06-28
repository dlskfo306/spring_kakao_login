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
public class Profile {
  
    public String nickname;
    public String thumbnail_image_url;
    public String profile_image_url;
    public Boolean is_default_image;

}
