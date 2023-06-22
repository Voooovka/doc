package com.faucet.mapper;


import com.faucet.model.User;
import com.faucet.dto.user.UserDto;

public class Mapper {

    private Mapper() {
    }

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getEmail(),
                user.getUsername(),
                user.getMoney()
        );
    }
}
