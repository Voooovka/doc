package com.faucet.service;

import java.util.List;

import com.faucet.dto.user.UserDto;
import com.faucet.model.User;
import com.faucet.dto.user.SendMoneyDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    List<UserDto> getUsers();
    UserDto getUser(String id);
    UserDto earnMoney(String email);
    UserDto sendMoney(SendMoneyDto sendMoneyDto);
    User getUserByEmail(String email);
    User createUser(User user);

}
