package obj;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jonas on 9/8/2015.
 */
public class PetMobile {
    private UUID IdMobile;
    private int IdWeb;
    private String Nome;
    private Date DataNascimento;
    private int RacaId;
    private int GeneroId;
    private int TipoPetId;

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Date getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        DataNascimento = dataNascimento;
    }

    public int getRacaId() {
        return RacaId;
    }

    public void setRacaId(int racaId) {
        RacaId = racaId;
    }

    public int getGeneroId() {
        return GeneroId;
    }

    public void setGeneroId(int generoId) {
        GeneroId = generoId;
    }

    public int getTipoPetId() {
        return TipoPetId;
    }

    public void setTipoPetId(int tipoPetId) {
        TipoPetId = tipoPetId;
    }

    public UUID getIdMobile() {
        return IdMobile;
    }

    public void setIdMobile(UUID idMobile) {
        IdMobile = idMobile;
    }

    public int getIdWeb() {
        return IdWeb;
    }

    public void setIdWeb(int idWeb) {
        IdWeb = idWeb;
    }
}
