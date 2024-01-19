package pt.ulusofona.lp2.deisichess;

public class PecaTorreH extends Peca {
    public int pontos = 3;
    public PecaTorreH(String identificador, String tipoDePeca, String equipa, String alcunha) {
        super(identificador, tipoDePeca, equipa, alcunha);
    }

    @Override
    public void setPng() {
        if (equipa.equals("10")) {
            png = "torre_h_black.png";
        } else {
            png = "torre_h_white.png";
        }
    }

    @Override
    public String toString() {
        if(estado.equals("capturado")) {
            return identificador + " | TorreHor | 3 | " + equipa + " | " + alcunha + " @ (n/a)";
        }

        return identificador + " | TorreHor | 3 | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
    }

    @Override
    public int getPontos() {
        return pontos;
    }
}
