package guru.springframework;

public class Money {

    int amount;

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Money money)) {
            return false;
        }
        return amount == money.amount;
    }
}
