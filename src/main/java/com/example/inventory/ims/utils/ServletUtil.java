package com.example.inventory.ims.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import com.example.inventory.ims.entities.User;
import com.example.inventory.ims.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ServletUtil {
    private static UserRepository userRepository;

    @Autowired
    public ServletUtil(UserRepository userDao) {
        ServletUtil.userRepository = userDao;
    }

    public static User getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Login required");
        }

        User userEntity = ServletUtil.getSessionUser();
        if (userEntity != null) {
            return userEntity;
        }
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("UserPrinciple :: " + principle);

        if (principle == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Principle is not defined");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmailAndIsDeletedIsFalse(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unauthorised access"));
        ServletUtil.setSessionUser(user);
        return user;
    }

    public static <User> com.example.inventory.ims.entities.User getSessionUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            HttpSession session = request.getSession(false);

            if (session == null) {
                return null;
            }
            return (com.example.inventory.ims.entities.User) session.getAttribute("LOGIN_USER");
        }

        return null;
    }

    public static <User> void setSessionUser(com.example.inventory.ims.entities.User userEntity) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            HttpSession session = request.getSession(false);

            if (session != null) {
                session.setAttribute("LOGIN_USER", userEntity);
            }
        }

        return;
    }
}
