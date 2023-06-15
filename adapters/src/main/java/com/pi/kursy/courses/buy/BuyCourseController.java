package com.pi.kursy.courses.buy;

import com.pi.kursy.security.configuration.UserDetailsServiceImpl;
import com.pi.kursy.shared.ErrorResponse;
import com.pi.kursy.shared.GenericException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class BuyCourseController {

    private final BuyCourseFacade facade;

    @PostMapping("/courses/{id}/buy")
    void buyCourse(
            @PathVariable String id,
            UsernamePasswordAuthenticationToken authentication
    ) throws BuyCourseFactory.CreateEntityException, BuyCourseEntity.BuyCourseValidationException {
        var clientId = ((UserDetailsServiceImpl.UserPrincipal) authentication.getPrincipal()).getId();
        var dto = new BuyCourseDto(id, clientId);
        facade.buyCourse(dto);
    }

    @ExceptionHandler(GenericException.class)
    ResponseEntity<ErrorResponse> handleError(GenericException error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.fromGenericException(error)
        );
    }

}
