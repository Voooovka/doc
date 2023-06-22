package com.faucet.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.faucet.mapper.Mapper;
import com.faucet.model.User;
import com.faucet.security.JwtTokenProvider;
import com.faucet.service.UserService;
import com.faucet.dto.auth.AuthenticationRequestDto;
import com.faucet.dto.user.SendMoneyDto;
import com.faucet.dto.user.UserDto;
import com.faucet.dto.user.UserRegistrationDto;
import com.faucet.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/app/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.token.expiration}")
    private Long tokenExpiration;

    @GetMapping("/all")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PutMapping("/earn-money")
    public UserDto makeMoney(@RequestBody UserDto userDto) {
        return userService.earnMoney(userDto.getEmail());
    }

    @PutMapping("/send-money")
    public UserDto sendMoney(@RequestBody SendMoneyDto dto) {
        return userService.sendMoney(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.FORBIDDEN);
        }
        User user = userService.getUserByEmail(request.getEmail());
        Map<Object, Object> response = new HashMap<>();
        response.put("user", Mapper.toDto(userService.getUserByEmail(request.getEmail())));
        response.put("token", jwtTokenProvider.createToken(user.getEmail(), Role.USER.name(), tokenExpiration));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRegistrationDto userRegistrationDto,
                                    BCryptPasswordEncoder bCryptPasswordEncoder) {
        String token = jwtTokenProvider.createToken(userRegistrationDto.getEmail(), Role.USER.name(), tokenExpiration);
        Map<Object, Object> response = new HashMap<>();
        response.put("user", Mapper.toDto(userService.createUser(new User(
                userRegistrationDto.getUsername(),
                userRegistrationDto.getEmail(),
                bCryptPasswordEncoder.encode(userRegistrationDto.getPassword()),
                0L))));
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate(@RequestHeader("token") String token) {
        return ResponseEntity.ok(jwtTokenProvider.validateToken(token));
    }


}


