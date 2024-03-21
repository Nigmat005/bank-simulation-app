package com.cydeo.service.impl;

import com.cydeo.enums.AccountType;
import com.cydeo.exception.AccountOwnershipException;
import com.cydeo.exception.BadRequestException;
import com.cydeo.exception.BalanceNotSufficientException;
import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final TransactionRepository transactionRepository;
    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate) {
        /*
         - if sender or receiver is null?
         - if sender or receiver is same account?
         - if sender has enough balance to make transfer?
         */
        validateAccount(sender,receiver);
        checkAccountOwnership(sender,receiver);
        executeBalanceAndUpdateIfRequired(amount,sender,receiver);

        /*
         after all validation are completed, and money is transferred m we need to create Transaction object
         */
        Transaction transaction=Transaction.builder().amount(amount).sender(sender.getId())
                .receiver(receiver.getId()).transActionDate(creationDate)
                .build();
        // make transfer
        return transactionRepository.save(transaction);
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) {
        if(checkSenderBalance(sender,amount)){
            // update sender and receiver
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        }
        else {
            throw new BalanceNotSufficientException("Balance is not enough for this transfer");
        }
    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {
        // Verify if sender has enough balance to make transfer
//       if(sender.getBalance().compareTo(amount)>=0){
//           return true;
//       }
//       else {
//           return false;
//       }
//        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO)>=0;
        return sender.getBalance().compareTo(amount)>=0;
    }

    private void checkAccountOwnership(Account sender, Account receiver) {

        if(sender.getAccountType().equals(AccountType.SAVING) || receiver.getAccountType().equals(AccountType.SAVING)
        &&!Objects.equals(sender.getUserID(), receiver.getUserID())){
            throw new AccountOwnershipException("If one of the account is saving, sender's UserId must be same with receiver's UserId");
        }

    }

    /**
     *          -if any of the account is null?
     *          -if accountIds are the same(same account)
     *          -if account is exist(repository)
     * @param sender
     * @param receiver
     */
    private void validateAccount(Account sender, Account receiver){
       //-if any of the account is null?
        if(sender==null || receiver ==null) {
            throw new BadRequestException("sender or receiver can not be null");
        }

        //  -if accountIds are the same throw BadRequestException with message "accounts need to be different"
        if(sender.getId()==receiver.getId()){
            throw new BadRequestException("accounts need to be different");
        }
        //-if account is exist(repository)
        findAccountById(sender.getId());
        findAccountById(sender.getId());

    }

    private void findAccountById(UUID uuid) {
        accountRepository.findById(uuid);
    }

    @Override
    public List<Transaction> findAllTransaction() {
        return null;
    }
}
