package com.pi.kursy.security.authentication.login;

import com.pi.kursy.security.authentication.logout.LogoutFacade;
import com.pi.kursy.security.shared.RoleEnum;
import com.pi.kursy.shared.ErrorResponse;
import com.pi.kursy.shared.GenericException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
class LoginController {

    private final LoginFacade facade;

    @PostMapping("/login")
    LoginResponse login(@RequestBody LoginRequest request) throws LoginFacade.LoginException {
        var responseDto = facade.login(request.toDto());
        return LoginResponse.fromDto(responseDto);
    }

    @PostMapping("/refresh")
    LoginResponse refresh(@RequestHeader("Authorization") String authorizationHeader) throws LogoutFacade.InvalidTokenException {
        var responseDto = facade.refresh(authorizationHeader);
        return LoginResponse.fromDto(responseDto);
    }

    @ExceptionHandler(GenericException.class)
    ResponseEntity<ErrorResponse> handleError(GenericException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.fromGenericException(error)
        );
    }

    record LoginRequest(String username, String password) {
        LoginDto toDto() {
            return new LoginDto(
                    username,
                    password
            );
        }
    }
    record LoginResponse(
            String token,
            String username,
            RoleEnum role) {
        static LoginResponse fromDto(LoginResponseDto dto) {
            return new LoginResponse(
                    dto.token(),
                    dto.username(),
                    dto.role()
            );
        }
    }
}
