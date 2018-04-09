package com.jldata.smartframe.core.simple.service;


import com.jldata.smartframe.core.security.JwtUserFactory;
import com.jldata.smartframe.core.simple.model.User;
import com.jldata.smartframe.core.simple.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by stephan on 20.03.16.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisCacheManager redisCacheManager;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Cache cache = redisCacheManager.getCache("userdetail_");
        User user = cache.get(username,User.class);
        // 从数据库内读用户数据，TODO :需改造成从cache中读
        //User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
