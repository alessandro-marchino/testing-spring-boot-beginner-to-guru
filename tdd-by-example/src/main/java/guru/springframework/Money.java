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

    public Expression times(int multiplier) {
        return new Money(amount * multiplier, this.currency);
    }

    public String currency() {
        return currency;
    }

    @Override
    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }

    @Override
    public Money reduce(Bank bank, String to) {
        return new Money(amount / bank.rate(this.currency, to), to);
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
