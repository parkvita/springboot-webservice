package com.parkvita.admin.springboot.config.auth.dto;

import com.parkvita.admin.springboot.domain.user.Role;
import com.parkvita.admin.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

// OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture){
        this.attributes = getAttributes();
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    // OAuth2User 에서 반환하는 사용자 정보는 Map 이기 때문에 값 하나하나를 변환해야만 한다
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttrivuteName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String)attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttrivuteName)
                .build();
    }

    // User 엔티티를 생성, OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할때
    // 가입할때의 기본 권한을 guest로 주기 위해서 role빌더값에는 Role.GUEST를 사용
    // OAuthAttributes 클래스 생성이 끝났으면 같은 패키지에 SeesionUser클래스를 생성
    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
