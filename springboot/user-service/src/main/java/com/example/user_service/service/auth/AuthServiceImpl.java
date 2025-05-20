package com.example.user_service.service.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.user_service.dto.auth.AuthResponseDTO;
import com.example.user_service.dto.auth.LoginRequestDTO;
import com.example.user_service.dto.user.UserRequestDTO;
import com.example.user_service.dto.user.UserResponseDTO;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.model.Role;
import com.example.user_service.model.User;
import com.example.user_service.repository.RoleRepository;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.JwtService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    public AuthResponseDTO<UserResponseDTO> login(LoginRequestDTO loginRequestDTO) {
        String userName = loginRequestDTO.getUserName();
        String password = loginRequestDTO.getPassword();

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Mật khẩu không hợp lệ");
        }
        UserResponseDTO response = userMapper.toDTO(user);

        String accessToken = jwtService.generateAccessToken(userName);
        String refreshToken = jwtService.generateRefreshToken(userName);

        return new AuthResponseDTO<>(accessToken, refreshToken, response);
    }

    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        Role role;
        if (userRequestDTO.getRole() != null) {
            role = roleRepository.findById(userRequestDTO.getRole())
                    .orElseThrow(
                            () -> new EntityNotFoundException("Role not found with id: " + userRequestDTO.getRole()));
        } else {
            role = roleRepository.findByName("User")
                    .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        }
        user.setRole(role);
        User data = userRepository.save(user);
        return userMapper.toDTO(data);
    }

}