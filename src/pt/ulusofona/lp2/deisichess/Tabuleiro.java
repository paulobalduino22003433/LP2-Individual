package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class Tabuleiro {
    public int tamanhoTabuleiro = -1, numPecaTotal = -1, linhaDoFicheiro=1;
    public boolean isBlackTurn = true, isWhiteTurn = false,isYellowTurn=true, houvePecaMorta = false;
    public ArrayList<Peca> whiteTeam;
    public ArrayList<Peca> blackTeam;
    public boolean isWhiteVsBlackGame=false;
    public boolean isYellowVsWhiteGame=false;
    public boolean isYellowVsBlackGame=false;


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
        if (isWhiteVsBlackGame){
            if (isBlackTurn) {
                isBlackTurn = false;
                isWhiteTurn = true;
            } else {
                isWhiteTurn = false;
                isBlackTurn = true;
            }
        }
        if (isYellowVsWhiteGame){
            if (isYellowTurn) {
                isYellowTurn = false;
                isWhiteTurn = true;
            } else {
                isWhiteTurn = false;
                isYellowTurn = true;
            }
        }

        if (isYellowVsBlackGame){
            if (isYellowTurn) {
                isYellowTurn = false;
                isBlackTurn = true;
            } else {
                isBlackTurn = false;
                isYellowTurn = true;
            }
        }
    }

    public boolean getIsBlackTurn() {
        return isBlackTurn;
    }

    public boolean getIsWhiteTurn() {
        return isWhiteTurn;
    }

    public boolean getIsYellowTurn(){
        return isYellowTurn;
    }

    public boolean algumaPecaMorreu() {
        return houvePecaMorta;
    }

    public void umaPecaMorreu() {
        houvePecaMorta = true;
    }
}
