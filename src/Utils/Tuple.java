package Utils;

public class Tuple<T, U> {

    private T x;
    private U y;


    public Tuple(T t, U u){
        this.x = t;
        this.y = u;
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
