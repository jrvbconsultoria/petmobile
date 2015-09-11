package obj;

/**
 * Created by Jonas_Spohr on 9/10/2015.
 */
public class PetRaca {
    public PetRaca(int id, String nome){
        this.RacaId = id;
        this.RacaNome = nome;
    }

    public int getRacaId(){
        return RacaId;
    }

    public void setRacaId(int id){
        RacaId = id;
    }

    public String getRacaNome() {
        return RacaNome;
    }

    public void setRacaNome(String racaNome) {
        RacaNome = racaNome;
    }

    private int RacaId;
    private String RacaNome;
}
