package com.chat.app.chatapp.services;

import com.chat.app.chatapp.dto.user.UserResponse;
import com.chat.app.chatapp.mapper.UserMapper;
import com.chat.app.chatapp.model.user.User;
import com.chat.app.chatapp.repository.UserRepository;
import com.chat.app.chatapp.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserDetailsById(String id) {
        return UserPrincipal.create(findUserById(id));
    }

    public UserResponse getUserResponse(String id) {
        return UserMapper.mapUserToUserResponse(findUserById(id));
    }

    public User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id)
        );
    }

}
