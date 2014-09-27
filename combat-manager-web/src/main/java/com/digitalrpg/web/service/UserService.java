package com.digitalrpg.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.digitalrpg.domain.dao.UserDao;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.web.controller.model.UserVO;
import com.google.common.base.Function;

public class UserService implements UserDetailsService {

    public static final Function<User, UserVO> userToVOFuction = new Function<User, UserVO>() {

        @Override
        public UserVO apply(User input) {
            if (input == null) {
                return null;
            }
            UserVO output = new UserVO();
            output.setName(input.getName());
            output.setId(input.getId());
            output.setEmail(input.getEmail());
            return output;
        }

    };

    @Autowired
    private UserDao userDao;

    public User findByMail(String email) {
        return userDao.findByMail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("Cannot find user " + username);
        }
        return new UserWrapper(user);
    }

    public User get(String userToUsername) {
        return userDao.get(userToUsername);
    }

}
