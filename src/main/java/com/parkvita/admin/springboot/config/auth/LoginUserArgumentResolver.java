package com.parkvita.admin.springboot.config.auth;

import com.parkvita.admin.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

// HandlerMethodArgumentResolver는 한가지 기능을 지원하는데 바로 조건에 맞는 경우 메소드가 있다면 이 클래스의 구현체가 지정한 값으로 해당 메소드의 파라미터로 넘길 수 있습니다.
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {   // 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단합니다. 여기서는 파라미터에 @LoginUser 어노테이션이 붙어있고,
                                                                    // 파라미터 클래스타입이 SessionUser.class 인경우 True 를 반환합니다.

        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }

    @Override   //파라미터에 전단할 객체를 생성합니다. 여기서는 세션에서 객체를가져옵니다.
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
    // 스프링에서 인식할수 있도록 WebMvcConfigurer에 추가
}
