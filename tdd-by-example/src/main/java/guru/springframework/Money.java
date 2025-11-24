package guru.springframework;

public abstract class Money {

    public static Money dollar(int amount) {
        return new Dollar(amount, "USD");
    }
    public static Money franc(int amount) {
        return new Franc(amount, "CHF");
    }

    protected String currency;
    protected int amount;

    protected Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public abstract Money times(int multiplier);

    public String currency() {
        return currency;
    }

    @Override
    public boolean equals(Object obj) {
        if(!getClass().isInstance(obj)) {
            return false;
        }
        return amount == ((Money) obj).amount;
    }
}
