package guru.springframework;

public abstract class Money {

    public static Money dollar(int amount) {
        return new Dollar(amount);
    }
    public static Money franc(int amount) {
        return new Franc(amount);
    }

    int amount;

    public abstract Money times(int multiplier);

    @Override
    public boolean equals(Object obj) {
        if(!getClass().isInstance(obj)) {
            return false;
        }
        return amount == ((Money) obj).amount;
    }
}
