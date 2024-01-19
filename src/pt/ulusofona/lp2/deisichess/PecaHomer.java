package pt.ulusofona.lp2.deisichess;

public class PecaHomer extends Peca {
    public int pontos = 2;
    public PecaHomer(String identificador, String tipoDePeca, String equipa, String alcunha) {
        super(identificador, tipoDePeca, equipa, alcunha);
    }

    String status ="a dormir";

    @Override
    public void setPng() {
        if (equipa.equals("10")) {
            png = "homer_black.png";
        } else {
            png = "homer_white.png";
        }
    }
    public String acordaOuDorme() {
        if (GameManager.nrTurno % 3 == 0) {
            status = "a dormir";
        } else {
            status = "acordado";
        }
        return status;
    }



    @Override
    public String toString() {
        acordaOuDorme();
        if (estado.equals("em jogo")){
            if (status.equals("a dormir") || GameManager.nrTurno==0){
                return "Doh! zzzzzz";
            } else{
                status="acordado";
                return identificador + " | Homer Simpson | " + "2 | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
            }
        }
        if(estado.equals("capturado")) {
            return identificador + " | Homer Simpson | 2 | " + equipa + " | " + alcunha + " @ (n/a)";
        }
        return identificador + " | Homer Simpson | " + "2 | " + equipa + " | " + alcunha + " @ (" + x + ", " + y + ")";
    }


    public boolean isSleeping(){
        if (status.equals("a dormir")){
            return true;
        }
        return false;
    }

    public boolean isAwake(){
        if (status.equals("acordado")){
            return true;
        }
        return false;
    }


    @Override
    public int getPontos() {
        return pontos;
    }
}
