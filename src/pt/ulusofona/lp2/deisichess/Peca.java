package pt.ulusofona.lp2.deisichess;

import java.util.List;

public class Peca {
    public String identificador;
    public String tipoDePeca;
    public String equipa;
    public String alcunha;
    public String png;
    public String estado;
    public String x = "";
    public String y = "";

    public Peca(String identificador, String tipoDePeca, String equipa, String alcunha) {
        this.identificador = identificador;
        this.tipoDePeca = tipoDePeca;
        this.equipa = equipa;
        this.alcunha = alcunha;
        this.estado = "em jogo";
        setPng();
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getTipoDePeca() {
        return tipoDePeca;
    }

    public String getEquipa() {
        return equipa;
    }

    public String getAlcunha() {
        return alcunha;
    }

    public String getEstado() {
        return estado;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getPng() {
        return png;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setPng() {
        if (equipa.equals("10")) {
            png = "king_black.png";
        } else {
            png = "king_white.png";
        }
    }

    public void estadoPecaCapturado() {
        estado = "capturado";
    }

    public static Peca getPecaByCoordinates(int x, int y, List<Peca> pecas) {
        for (Peca peca : pecas) {
            if (peca.getX().equals(Integer.toString(x)) && peca.getY().equals(Integer.toString(y))) {
                return peca;
            }
        }
        return null;
    }


    public int getPontos() {
        //Implementação default retorna 0, override nas subclasses
        return 0;
    }
    @Override
    public String toString() {
        return "";
    }
}
