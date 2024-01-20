package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class Tabuleiro {
    public int tamanhoTabuleiro = -1, numPecaTotal = -1, linhaDoFicheiro=1;
    public boolean isBlackTurn, isWhiteTurn, isYellowTurn, houvePecaMorta = false;
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

    public boolean changeTurnInGame() {
        if (isBlackTurn) {
            if (yellowTeam.isEmpty()){
                isBlackTurn=false;
                isWhiteTurn=true;
                return true;
            }else{
                isBlackTurn=false;
                isYellowTurn=true;
                return true;
            }
        }
        if (isWhiteTurn){
            if (yellowTeam.isEmpty()){
                isWhiteTurn=false;
                isBlackTurn=true;
                return true;
            }else{
                isWhiteTurn=false;
                isYellowTurn=true;
                return true;
            }
        }

        if (isYellowTurn){
            if (blackTeam.isEmpty()){
                isYellowTurn=false;
                isWhiteTurn=true;
                return true;
            }else{
                isYellowTurn=false;
                isBlackTurn=true;
                return true;
            }
        }
        return true;
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

    public void decideGameTeams(){
        if (blackTeam.isEmpty()){
           isYellowVsWhiteGame=true;
        }
        if (whiteTeam.isEmpty()){
           isYellowVsBlackGame=true;
        }
        if (yellowTeam.isEmpty()){
           isWhiteVsBlackGame=true;
        }

        if (isWhiteVsBlackGame){
            isBlackTurn=true;
            isWhiteTurn=false;
        }

        if (isYellowVsBlackGame){
            isBlackTurn=true;
            isYellowTurn=false;
        }

        if (isYellowVsWhiteGame){
            isYellowTurn=true;
            isWhiteTurn=false;
        }
    }
}
