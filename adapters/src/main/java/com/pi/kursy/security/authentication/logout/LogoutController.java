package com.pi.kursy.security.authentication.logout;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
class LogoutController {

    private final LogoutFacade logoutFacade;

    @PostMapping("/logout")
    void logout(@RequestHeader("Authorization") String authorizationHeader) throws LogoutFacade.InvalidTokenException {
        logoutFacade.logout(authorizationHeader);
    }
}
