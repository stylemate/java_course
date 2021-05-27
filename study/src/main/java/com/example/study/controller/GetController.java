package com.example.study.controller;

import com.example.study.model.SearchParam;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GetController {

    @RequestMapping(method = RequestMethod.GET, path = "/getMethod")
    public String getRequest() {
        return "Hi getMethod";
    }

    @GetMapping("/getParameter")
    public String getParameter(@RequestParam String id, @RequestParam(name = "password") String pwd) {
        String password = "bbbb";
        System.out.println("id: " + id);
        System.out.println("password: " + password);

        return id + pwd + password;
    }

    @GetMapping("/getMultiParameter")
    public SearchParam getMultiParameter(SearchParam searchParam) {
        //아 lombok compile은 되는데 IDE에뜨는 오류는 안없어진다;;
        System.out.println(searchParam.getAccount());
        System.out.println(searchParam.getEmail());
        System.out.println(searchParam.getPage());
        //Just by changing return type, it will convert to json format
        return searchParam;
    }
}
