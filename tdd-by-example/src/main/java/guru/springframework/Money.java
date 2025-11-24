package guru.springframework;

public class Money implements Expression {

    public static Money dollar(int amount) {
        return new Money(amount, "USD");
    }
    public static Money franc(int amount) {
        return new Money(amount, "CHF");
    }

    protected String currency;
    protected int amount;

    protected Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money times(int multiplier) {
        return new Money(amount * multiplier, this.currency);
    }

    public String currency() {
        return currency;
    }

    public Expression plus(Money addend) {
        return new Money(amount + addend.amount, currency);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Money money)) {
            return false;
        }
        return amount == money.amount
            && currency.equals(money.currency);
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Money [currency=").append(currency).append(", amount=").append(amount).append("]");
        return builder.toString();
    }
    
}
