package com.example.study.controller;

import com.example.study.model.SearchParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {
    //same thing
    //@RequestMapping(method = RequestMethod.POST, path ="/postMethod")
    //POST data is in request body, as json/xml/multipart-form/text-plain
    @PostMapping(value = "/postMethod")
    public SearchParam postMethod(@RequestBody SearchParam searchParam) {
        return searchParam;
        //test with postman, content-type application/json as header, json format in body raw
    }

    @PutMapping(value = "/putMethod")
    public void put() {

    }

    @PatchMapping(value = "/patchMethod")
    public void patch() {

    }
}
