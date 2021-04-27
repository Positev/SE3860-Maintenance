package Utils;

import java.util.Objects;

public class Tuple<T, U> {

    private T x;
    private U y;


    public Tuple(T t, U u){
        this.x = t;
        this.y = u;
    }

    @Override
    public String toString() {
        return "new Tuple(" + x + "," + y + ");";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return getX().equals(tuple.getX()) &&
                getY().equals(tuple.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public U getY() {
        return y;
    }

    public void setY(U y) {
        this.y = y;
    }
}
