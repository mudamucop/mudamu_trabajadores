
package com.Mudamu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String getCustomError(@RequestHeader(name = "code") String errorCode) {

        return "404";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}