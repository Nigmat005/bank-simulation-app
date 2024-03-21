package com.cydeo.service.impl;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
@Component
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

   @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId) {
        //we need to create the account object
        Account account=Account.builder().id(UUID.randomUUID()).userID(userId).balance(balance)
                .creationDate(createDate)
                .accountType(accountType)
                .build();

        //save into the database(repository)
//        //return the object created
        return accountRepository.save(account);
    }

    @Override
    public List<Account> listAllAccount() {
        return accountRepository.findAll();
    }


//    @Override
//    public Account createNewAccount(Long userId,BigDecimal balance, Date createDate, AccountType accountType ) {
//        //we need to create the account object
//        Account account=Account.builder().id(UUID.randomUUID()).userID(userId).balance(balance)
//                .creationDate(createDate).accountType(accountType)
//                .build();
//
//        //save into the database(repository)
//        //return the object created
//        return accountRepository.save(account);
//    }
//
//
//    @Override
//    public List<Account> listAllAccount() {
//        // we need to get all the accounts from repository
//        return accountRepository.findAll();
//    }
}
