package pl.mzuchnik.orderms.domain;

import java.math.BigDecimal;

public record Price(BigDecimal amount) {

    public static final Price ZERO = new Price(BigDecimal.ZERO);

    public Price{
        if(amount == null){
            throw new NullPointerException("Amount is null");
        }
       /* if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount must be greater than zero");
        }*/
    }

    public Price multiply(BigDecimal multiplicand) {
        if(multiplicand == null){
            throw new IllegalArgumentException("Multiplicand must be null");
        }
        return new Price(amount.multiply(multiplicand));
    }

    public Price add(Price price) {
        if(price == null){
            throw new NullPointerException("Price is null");
        }
        return new Price(amount.add(price.amount));
    }
}
