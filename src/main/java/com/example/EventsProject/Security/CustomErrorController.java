package com.example.EventsProject.Security;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public class CustomErrorController implements ErrorController {

        @RequestMapping("/error")
        public String handleError(HttpServletRequest request) {
           Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
            if (status != null && status.toString().equals("404")) {
                return "error404";
            }
            return "error";
        }


       public String getErrorPath() {
          return "/error";
       }
    }

