package guru.springframework;

public class Franc {

    private int amount;

    public Franc(int amount) {
        this.amount = amount;
    }

    Franc times(int multiplier) {
        return new Franc(amount * multiplier);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Franc dollar)) {
            return false;
        }
        return amount == dollar.amount;
    }
}
