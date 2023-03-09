package com.pi.kursy.security.authentication.login;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
class LoginController {

    private final LoginFacade facade;

    @PostMapping("/login")
    LoginResponse login(@RequestBody LoginRequest request) throws LoginFacade.LoginException {
        var responseDto = facade.login(LoginRequest.toDto(request));
        return LoginResponse.fromDto(responseDto);
    }

    record LoginRequest(String username, String password) {
        static LoginDto toDto(LoginRequest request) {
            return new LoginDto(
                    request.username,
                    request.password
            );
        }
    }
    record LoginResponse(String token) {
        static LoginResponse fromDto(LoginResponseDto dto) {
            return new LoginResponse(dto.token());
        }
    }
}