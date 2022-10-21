package edu.miu.sa.reservation.controller;

import edu.miu.sa.reservation.domain.LoginResponse;
import edu.miu.sa.reservation.security.JwtHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/login")
public class LoginController {

    private final JwtHelper jwtHelper;

    @PostMapping("/{email}")
    public LoginResponse login(@PathVariable String email) {
        return new LoginResponse(jwtHelper.generateToken(email));
    }
}
