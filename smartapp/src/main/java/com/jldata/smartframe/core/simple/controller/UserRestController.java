package com.jldata.smartframe.core.simple.controller;


import com.jldata.smartframe.core.common.RestResult;
import com.jldata.smartframe.core.security.JwtTokenUtil;
import com.jldata.smartframe.core.security.JwtUser;
import com.jldata.smartframe.core.simple.model.Authority;
import com.jldata.smartframe.core.simple.model.User;
import com.jldata.smartframe.core.simple.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public RestResult getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return new RestResult(user);
    }


    @RequestMapping("user/info")
    public RestResult getUserInfo(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        User user = userRepository.findByUsername(username);
        Map<String,Object> r = new HashMap<String,Object>();
        List<Authority> authrities = user.getAuthorities();
        if(authrities!=null){
            List<String> roles = new ArrayList<String>();
            for(Authority a:authrities){
                roles.add(a.getName().name());
            }
            r.put("roles",roles);
        }
        r.put("name",username);
        return new RestResult(r);
    }
    @RequestMapping(value = "user/logout")
    public ResponseEntity<?> logout()
    {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok(new RestResult(""));
    }

}
