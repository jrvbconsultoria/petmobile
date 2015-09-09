package obj;

/**
 * Created by jonas on 9/8/2015.
 */
public enum TipoPetEnum {
    CACHORRO(1), GATO(2), OUTRO(3);
    private final int value;

    private TipoPetEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}


