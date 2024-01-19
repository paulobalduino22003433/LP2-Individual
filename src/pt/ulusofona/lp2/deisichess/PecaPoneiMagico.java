package pt.ulusofona.lp2.deisichess;

public class PecaPoneiMagico extends Peca {
    public int pontos = 5;
    public PecaPoneiMagico(String identificador, String tipoDePeca, String equipa, String alcunha) {
        super(identificador, tipoDePeca, equipa, alcunha);
    }

    @Override
    public void setPng() {
        if (equipa.equals("10")) {
            png = "ponei_magico_black.png";
        } else {
            png = "ponei_magico_white.png";
        }
    }

    @Override
    public String toString() {
        if(estado.equals("capturado")) {
            return identificador + " | Ponei Mágico | 5 | " + equipa + " | " + alcunha + " @ (n/a)";
        }

        return identificador + " | Ponei Mágico | 5 | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
    }

    @Override
    public int getPontos() {
        return pontos;
    }
}
