package guru.springframework;

public class Dollar {

    int amount;

    public Dollar(int amount) {
        this.amount = amount;
    }

    Dollar times(int multiplier) {
        return new Dollar(amount * multiplier);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Dollar dollar)) {
            return false;
        }
        return amount == dollar.amount;
    }
}
