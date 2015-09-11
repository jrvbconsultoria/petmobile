package obj;

/**
 * Created by Jonas_Spohr on 9/10/2015.
 */
public class GeneroResource {
    public static PetGenero[] GetGeneros(){
        return new PetGenero[] {
                new PetGenero(0, "Selecione"),
                new PetGenero(1, "Macho"),
                new PetGenero(2, "Fêmea")
        };
    }
}
