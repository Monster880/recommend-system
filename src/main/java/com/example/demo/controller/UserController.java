package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.common.CommonUtil;
import com.example.demo.model.UserModel;
import com.example.demo.request.LoginReq;
import com.example.demo.request.RegisterReq;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.common.BusinessException;
import com.example.demo.common.CommonRes;
import com.example.demo.common.EmBusinessError;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Controller("/user")
@RequestMapping("/user")
public class UserController {
    public static final String CURRENT_USER_SESSION = "currentUserSession";

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserService userService;


    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        String userName = "123";
        ModelAndView modelAndView = new ModelAndView("/index.html");
        modelAndView.addObject("name", userName);
        return modelAndView;
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonRes getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        UserModel userModel = userService.getUser(id);
        if (userModel == null) {
            //return CommonRes.create(new CommonError(EmBusinessError.NO_OBJECT_FOUND),"fail");
            throw new BusinessException(EmBusinessError.NO_OBJECT_FOUND);
        } else {
            return CommonRes.create(userModel);
        }
    }

    @RequestMapping("/register")
    @ResponseBody
    public CommonRes register(@Valid @RequestBody RegisterReq registerReq, BindingResult bindingResult) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, CommonUtil.processErrorString(bindingResult));
        }

        UserModel registerUser = new UserModel();
        registerUser.setTelphone(registerReq.getTelphone());
        registerUser.setPassword(registerReq.getPassword());
        registerUser.setNickName(registerReq.getNickName());
        registerUser.setGender(registerReq.getGender());
        UserModel resUserModel = userService.register(registerUser);

        return CommonRes.create(resUserModel);
    }

    @RequestMapping("/login")
    @ResponseBody
    public CommonRes login(@RequestBody @Valid LoginReq loginReq, BindingResult bindingResult) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, CommonUtil.processErrorString(bindingResult));
        }
        UserModel userModel = userService.login(loginReq.getTelphone(), loginReq.getPassword());
        httpServletRequest.getSession().setAttribute(CURRENT_USER_SESSION, userModel);

        return CommonRes.create(userModel);
    }

    @RequestMapping("/logout")
    @ResponseBody
    public CommonRes logout() throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        httpServletRequest.getSession().invalidate();
        return CommonRes.create(null);
    }

    //????????????????????????
    @RequestMapping("/getcurrentuser")
    @ResponseBody
    public CommonRes getCurrentUser() {
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute(CURRENT_USER_SESSION);
        return CommonRes.create(userModel);
    }
}
