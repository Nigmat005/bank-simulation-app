package com.cydeo.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
@Data
@Builder
public class Transaction {
    @Builder.Default
    private UUID sender= UUID.randomUUID();
    @Builder.Default
    private UUID receiver=UUID.randomUUID();

    private BigDecimal amount;
    private String message;
    @Builder.Default
    private Date transActionDate=new Date();

}
