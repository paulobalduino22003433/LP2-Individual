package pt.ulusofona.lp2.deisichess;

public class PecaJohnMcClane extends Peca{

    int pontos = 20;

    public PecaJohnMcClane(String identificador, String tipoDePeca, String equipa, String alcunha) {
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
        return "Sou o John McClane. Yippee ki yay. Sou duro de roer, mas não me sei mover";
    }


    @Override
    public int getPontos() {
        return pontos;
    }


}
