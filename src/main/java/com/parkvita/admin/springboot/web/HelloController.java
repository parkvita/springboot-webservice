package com.parkvita.admin.springboot.web;

import com.parkvita.admin.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어줌
                // 예전에는 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 사용할수 있게 해줌
public class HelloController {

    @GetMapping("/hello")   // HTTP Method인 get의 요청을 받을수 있는 API 만들어줌
                            // @RequestMapping(method = RequestMethod.GET)으로 사용,
                            // 이제 이프로젝트는 /hello 요청이오면 hello를 반환하는 기능을 가짐
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
    }
}

