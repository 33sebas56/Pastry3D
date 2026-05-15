package com.sebastian.pastry3ddemo.service.auth;

import com.sebastian.pastry3ddemo.dto.auth.AuthResponse;
import com.sebastian.pastry3ddemo.dto.auth.LoginRequest;
import com.sebastian.pastry3ddemo.dto.auth.RegisterRequest;
import com.sebastian.pastry3ddemo.entity.AppUser;
import com.sebastian.pastry3ddemo.exception.ApiException;
import com.sebastian.pastry3ddemo.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();

        if (appUserRepository.existsByEmail(email)) {
            throw new ApiException(HttpStatus.CONFLICT, "Ya existe una cuenta con ese correo");
        }

        AppUser user = new AppUser();
        user.setName(request.name());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        AppUser saved = appUserRepository.save(user);
        String token = jwtService.generateToken(saved);

        return toResponse(saved, token);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase();

        AppUser user = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Credenciales invalidas"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BadCredentialsException("Credenciales invalidas");
        }

        String token = jwtService.generateToken(user);
        return toResponse(user, token);
    }

    private AuthResponse toResponse(AppUser user, String token) {
        return new AuthResponse(
                token,
                "Bearer",
                user.getId().toString(),
                user.getName(),
                user.getEmail()
        );
    }
}
