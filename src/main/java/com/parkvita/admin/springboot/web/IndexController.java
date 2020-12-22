package com.parkvita.admin.springboot.web;

import com.parkvita.admin.springboot.config.auth.dto.SessionUser;
import com.parkvita.admin.springboot.service.posts.PostsService;
import com.parkvita.admin.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    /*private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }*/

    // 로그인 추가
    private final PostsService postsService;
    private final HttpSession httpSession;
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts",postsService.findAllDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user");  // 앞서 작성된 CustomOAuth2UserService 에서 로그인 성공 시 세션에 SeessionUser를 저장하도록 구성
                                                                                    // 즉 로그인 성공시 httpSession.getAttribute("user") 에서 값을 가져올 수 있음
        if(user!=null){
            // 세션에 저장된 값이 있을때만 model에 userName 으로 등록, 저장된 값이 없으면 model엔 아무런 값이 없는 상태이니 로그인 버튼이 보임
            model.addAttribute("userName",user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);

        return "posts-update";
    }




}
