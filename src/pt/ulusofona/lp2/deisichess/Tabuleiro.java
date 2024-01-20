package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class Tabuleiro {
    public int tamanhoTabuleiro = -1, numPecaTotal = -1, linhaDoFicheiro=1;
    public boolean isBlackTurn = true, isWhiteTurn = false, isYellowTurn=false, houvePecaMorta = false;
    public ArrayList<Peca> whiteTeam;
    public ArrayList<Peca> blackTeam;

    public ArrayList<Peca> yellowTeam;

    public boolean isWhiteVsBlackGame=false;
    public boolean isYellowVsWhiteGame=false;
    public boolean isYellowVsBlackGame=false;

    public Tabuleiro(ArrayList<Peca> whiteTeam, ArrayList<Peca> blackTeam,ArrayList<Peca> yellowTeam) {
        this.whiteTeam = whiteTeam;
        this.blackTeam = blackTeam;
        this.yellowTeam = yellowTeam;
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
        } else if (isWhiteTurn) {
            isWhiteTurn = false;
            if (yellowTeam.size() > 0) {
                isYellowTurn = true;
            } else {
                isBlackTurn = true;
            }
        } else if (isYellowTurn) {
            isYellowTurn = false;
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
