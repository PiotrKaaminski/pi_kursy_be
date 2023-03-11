package com.pi.kursy.users.register;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Slf4j
class PasswordValidator {

    boolean isInvalid(String password) {
        if (!StringUtils.hasText(password)) {
            log.warn("password cannot be empty");
            return true;
        }
        if (StringUtils.containsWhitespace(password)) {
            log.warn("password cannot contain whitespace");
            return true;
        }
        if (password.length() < 5) {
            log.warn("password cannot have less than 5 characters");
            return true;
        }
        return false;
    }

}
