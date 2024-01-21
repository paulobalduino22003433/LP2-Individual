package pt.ulusofona.lp2.deisichess;

public class PecaJohnMcClane extends Peca{

    int pontos = 20;

    public PecaJohnMcClane(String identificador, String tipoDePeca, String equipa, String alcunha) {
        super(identificador, tipoDePeca, equipa, alcunha);
    }

    @Override
    public void setPng() {
        if (equipa.equals("10")) {
            png = "double-face-mask.png";
        } else {
            png = "white-mask.png";
        }
    }

    @Override
    public String toString() {
        if(estado.equals("capturado")) {
            return identificador + " | John McClane | 20 | " + equipa + " | " + alcunha + " @ (n/a)";
        }

        return identificador + " | John McClane | 20 | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
    }


    @Override
    public int getPontos() {
        return pontos;
    }


}
