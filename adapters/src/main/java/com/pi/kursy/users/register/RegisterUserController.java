package com.pi.kursy.users.register;

import com.pi.kursy.security.configuration.UserDetailsServiceImpl;
import com.pi.kursy.security.shared.RoleEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class RegisterUserController {

    private final RegisterUserFacade registerUserFacade;
    private final ChangePasswordFacade changePasswordFacade;

    @PostMapping("register")
    RegisterUserResponse register(@RequestBody RegisterUserRequest request) throws Exception {
        var responseDto = registerUserFacade.registerUser(request.toDto());
        return RegisterUserResponse.fromDto(responseDto);
    }

    @PatchMapping("password")
    void modifyPassword(
            @RequestBody ChangePasswordRequest request, UsernamePasswordAuthenticationToken authToken) throws Exception {
        var userId = ((UserDetailsServiceImpl.UserPrincipal) authToken.getPrincipal()).getId();
        changePasswordFacade.changePassword(request.toDto(userId));
    }

    record RegisterUserRequest(String username,String password, Role role) {
        RegisterUserDto toDto() {
            return new RegisterUserDto(
                    username,
                    password,
                    role.getRole()
            );
        }

        enum Role {
            ADMIN(RoleEnum.ADMIN),
            TEACHER(RoleEnum.TEACHER),
            STUDENT(RoleEnum.STUDENT);

            @Getter
            private final RoleEnum role;

            Role(RoleEnum role) {
                this.role = role;
            }
        }
    }

    record RegisterUserResponse(String id) {
        static RegisterUserResponse fromDto(RegisterUserResponseDto dto) {
            return new RegisterUserResponse(
                    dto.id()
            );
        }
    }

    record ChangePasswordRequest(String oldPassword, String newPassword) {
        ChangePasswordDto toDto(String userId) {
            return new ChangePasswordDto(userId, oldPassword, newPassword);
        }
    }
}
