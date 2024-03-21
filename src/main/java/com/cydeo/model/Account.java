package com.cydeo.model;

import com.cydeo.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
@Data
@AllArgsConstructor
@Builder
public class Account {
    private UUID id;
    private Long userID;
    private BigDecimal balance;
    private AccountType accountType;
    private Date creationDate;

}
