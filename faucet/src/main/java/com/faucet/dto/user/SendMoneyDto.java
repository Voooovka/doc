package com.faucet.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendMoneyDto {

    String emailFrom;
    String emailTo;
    Long amount;
}
