package obj;

/**
 * Created by Jonas_Spohr on 9/10/2015.
 */
public class PetGenero {
    public PetGenero(int id, String nome){
        this.GeneroId = id;
        this.GeneroNome = nome;
    }

    private int GeneroId;
    private String GeneroNome;

    public int getGeneroId() {
        return GeneroId;
    }

    public void setGeneroId(int generoId) {
        GeneroId = generoId;
    }

    public String getGeneroNome() {
        return GeneroNome;
    }

    public void setGeneroNome(String generoNome) {
        GeneroNome = generoNome;
    }
}
