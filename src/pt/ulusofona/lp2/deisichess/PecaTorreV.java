package pt.ulusofona.lp2.deisichess;

public class PecaTorreV extends Peca {
    public int pontos = 3;
    public PecaTorreV(String identificador, String tipoDePeca, String equipa, String alcunha) {
        super(identificador, tipoDePeca, equipa, alcunha);
    }

    @Override
    public void setPng() {
        if (equipa.equals("10")) {
            png = "torre_v_black.png";
        } else {
            png = "torre_v_white.png";
        }
    }

    @Override
    public String toString() {
        if(estado.equals("capturado")) {
            return identificador + " | TorreVert | 3 | " + equipa + " | " + alcunha + " @ (n/a)";
        }

        return identificador + " | TorreVert | 3 | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
    }

    @Override
    public int getPontos() {
        return pontos;
    }
}
