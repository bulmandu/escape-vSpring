package com.escapepj.demo.controller;

import com.escapepj.demo.mapper.UserMapper;
import com.escapepj.demo.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/*")
public class UserController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/login")
    public String  userLogin() {
        return "login";
    }

    @RequestMapping("/join")
    public String  joinUser() {
        return "join";
    }

    @ResponseBody
    @RequestMapping("/idCheck")
    public Map<String,String> idCheckAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("login/idcheck");
        Map<String,String> resultMap = new HashMap<String,String>();
        int check = 0;
        String result = "";

        String username = request.getParameter("username");
        check = userMapper.idCheck(username);
        if(check == 1) {
            result = "N";
        }else if(check == 0) {
            result = "Y";
        }
        System.out.println("result: " +result + ", username: " + username);
        resultMap.put("result", result);
        return resultMap;
    }

    @RequestMapping("/join/save")
    public String  saveUser(ModelMap model, UserVo userVo, HttpServletRequest request, HttpServletResponse response) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
        int result = 0;
        result =userMapper.saveUser(userVo);
        if(result == 1) {
            model.addAttribute("result", "Y");
        }else if(result == 0) {
            model.addAttribute("result", "N");
        }
        model.addAttribute("submit", "join");
        return "result";
    }

    @RequestMapping("/login/fail")
    public String  loginFailUser(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("result", "N");
        model.addAttribute("submit", "login");
        return "result";
    }


//    @RequestMapping("/openapi/readAuthority/{username}")
//    public @ResponseBody String  openApiReadAuthority(@PathVariable String username) {
//        List<String> auths = userMapper.readAuthority(username);
//
//        StringBuffer buf = new StringBuffer();
//        for(String auth : auths) {
//            buf.append(auth);
//            buf.append(" ");
//        }
//        return buf.toString();
//    }

}
