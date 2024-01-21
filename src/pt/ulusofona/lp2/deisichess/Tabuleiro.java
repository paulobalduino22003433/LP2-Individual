package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class Tabuleiro {
    public int tamanhoTabuleiro = -1, numPecaTotal = -1, linhaDoFicheiro=1;
    public boolean isBlackTurn = true, isWhiteTurn = false, houvePecaMorta = false;
    public ArrayList<Peca> whiteTeam;
    public ArrayList<Peca> blackTeam;

    public Tabuleiro(ArrayList<Peca> whiteTeam, ArrayList<Peca> blackTeam) {
        this.whiteTeam = whiteTeam;
        this.blackTeam = blackTeam;
    }

    public void incLinhaDoFicheiro(){
        linhaDoFicheiro++;
    }

    public int getLinhaDoFicheiro(){
        return linhaDoFicheiro;
    }
    public void setTamanhoTabuleiro(int tamanhoTabuleiro) {
        this.tamanhoTabuleiro = tamanhoTabuleiro;
    }

    public int getTamanhoTabuleiro() {
        return tamanhoTabuleiro;
    }

    public void setNumPecaTotal(int numPecaTotal) {
        this.numPecaTotal = numPecaTotal;
    }

    public int getNumPecaTotal() {
        return numPecaTotal;
    }

    public void changeTurnInGame() {
        if (isBlackTurn) {
            isBlackTurn = false;
            isWhiteTurn = true;
        } else {
            isWhiteTurn = false;
            isBlackTurn = true;
        }
    }

    public boolean getIsBlackTurn() {
        return isBlackTurn;
    }

    public boolean getIsWhiteTurn() {
        return isWhiteTurn;
    }

    public boolean algumaPecaMorreu() {
        return houvePecaMorta;
    }

    public void umaPecaMorreu() {
        houvePecaMorta = true;
    }
}
