package pt.ulusofona.lp2.deisichess;

public class PecaPadreVila extends Peca {
    public int pontos = 3;
    public PecaPadreVila(String identificador, String tipoDePeca, String equipa, String alcunha) {
        super(identificador, tipoDePeca, equipa, alcunha);
    }

    @Override
    public void setPng() {
        if (equipa.equals("10")) {
            png = "padre_vila_black.png";
        } else {
            png = "padre_vila_white.png";
        }
    }

    @Override
    public String toString() {
        if(estado.equals("capturado")) {
            return identificador + " | Padre da Vila | 3 | " + equipa + " | " + alcunha + " @ (n/a)";
        }

        return identificador + " | Padre da Vila | 3 | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
    }


    @Override
    public int getPontos() {
        return pontos;
    }
}
