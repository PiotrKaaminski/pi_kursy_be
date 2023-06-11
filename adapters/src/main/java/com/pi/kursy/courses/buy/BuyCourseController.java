package com.pi.kursy.courses.buy;

import com.pi.kursy.security.configuration.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    ) throws Exception {
        var clientId = ((UserDetailsServiceImpl.UserPrincipal) authentication.getPrincipal()).getId();
        var dto = new BuyCourseDto(id, clientId);
        facade.buyCourse(dto);
    }

}
