package guru.springframework;

public abstract class Money {

    int amount;

    @Override
    public boolean equals(Object obj) {
        if(!getClass().isInstance(obj)) {
            return false;
        }
        return amount == ((Money) obj).amount;
    }
}
