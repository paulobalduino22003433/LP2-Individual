package pt.ulusofona.lp2.deisichess;

public class PecaJoker extends Peca {
    public int pontos = 4;
    public PecaJoker(String identificador, String tipoDePeca, String equipa, String alcunha) {
        super(identificador, tipoDePeca, equipa, alcunha);
    }

    @Override
    public void setPng() {
        if (equipa.equals("10")) {
            png = "joker_black.png";
        } else {
            png = "joker_white.png";
        }
    }

    @Override
    public String toString() {
        if(estado.equals("capturado")) {
            return identificador + " | Joker | 4 | " + equipa + " | " + alcunha + " @ (n/a)";
        }
        switch (GameManager.turnoJoker){
            case 1:
                return identificador + " | Joker/Rainha | 4 | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
            case 2:
                return identificador + " | Joker/Ponei Mágico | 4 | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
            case 3:
                return identificador + " | Joker/Padre da Vila | 4 | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
            case 4:
                return identificador + " | Joker/TorreHor | 4 | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
            case 5:
                return identificador + " | Joker/TorreVert | 4 | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
            case 6:
                if (GameManager.nrTurno % 3 == 0) {
                    return "Doh! zzzzzz";
                }else {
                    return identificador + " | Joker/Homer Simpson | 4 | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
                }

            default: return identificador + " | Joker | 4 | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
        }
    }

    @Override
    public int getPontos() {
        return pontos;
    }
}
