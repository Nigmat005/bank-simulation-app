package com.cydeo.repository;

import com.cydeo.exception.BadRequestException;
import com.cydeo.exception.RecordNotFoundException;
import com.cydeo.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AccountRepository {
    // Create a list and use as simulated DB
    public static List<Account> accountList=new ArrayList<>();

    public Account save(Account account){
        accountList.add(account);
        return account;
    }

    public List<Account> findAll(){
       return accountList;
    }


    //Task complete method that find the account inside the accountList, if not throw RecordNotFoundException

    public Account findById(UUID id)  {
        //TASK
        //complete the method, that find the account inside the account list,if not
        //throw RecordNotFoundException
        return accountList.stream().filter(account -> account.getId().equals(id))
                .findAny().orElseThrow(()-> new RecordNotFoundException("Account does not exist in the database."));

    }


//    boolean checkAccount(Account account){
//        return accountList.stream().anyMatch(eachAcct->eachAcct.getId()==account.getId());
//    }
//
//    public void findAccount(Account account) throws RecordNotFoundException {
//        if(!checkAccount(account))
//            throw new RecordNotFoundException("Account not found");
//    }
}
