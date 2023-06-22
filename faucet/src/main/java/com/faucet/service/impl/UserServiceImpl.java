package com.faucet.service.impl;

import com.faucet.dto.user.SendMoneyDto;
import com.faucet.dto.user.UserDto;
import com.faucet.mapper.Mapper;
import com.faucet.model.User;
import com.faucet.repository.UserRepository;
import com.faucet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private static final Integer EARN_MONEY_PERIOD = 5;

    private final UserRepository userRepository;
    private final RedisTemplate<String, LocalDateTime> redisTemplate;

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(Mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(String email) {
        User user = userRepository.findUserByEmail(email);
        return Mapper.toDto(user);
    }

    @Override
    public UserDto earnMoney(String email) {
        LocalDateTime expireAt = redisTemplate.opsForValue().get(email);
        if (expireAt != null) {
            throw new IllegalArgumentException(String.format("Next 5$ you can get in %d seconds", ChronoUnit.SECONDS.between(LocalDateTime.now(), expireAt)));
        }
        User user = userRepository.findUserByEmail(email);
        if (user == null){
            throw new IllegalArgumentException("User not found!");
        }
        user.setMoney(user.getMoney() + 5);
        user = userRepository.save(user);
        redisTemplate.opsForValue().set(email, LocalDateTime.now().plusMinutes(EARN_MONEY_PERIOD), EARN_MONEY_PERIOD, TimeUnit.MINUTES);

        return Mapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto sendMoney(SendMoneyDto sendMoneyDto) {

        User sender = userRepository.findUserByEmail(sendMoneyDto.getEmailFrom());
        User receiver = userRepository.findUserByEmail(sendMoneyDto.getEmailTo());

        if (receiver == null || sender == null) {
            throw new IllegalArgumentException("There is no such user");
        }

        if (receiver.getEmail().equals(sender.getEmail())){
            throw new IllegalArgumentException("You can't send money to yourself!");
        }

        if (sender.getMoney() >= sendMoneyDto.getAmount()) {
            sender.setMoney(sender.getMoney() - sendMoneyDto.getAmount());
            receiver.setMoney(receiver.getMoney() + sendMoneyDto.getAmount());

            userRepository.save(receiver);
            return Mapper.toDto(userRepository.save(sender));
        } else throw new IllegalArgumentException("You have not enough money");
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }


}
