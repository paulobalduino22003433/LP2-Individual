package pt.ulusofona.lp2.deisichess;

public class InvalidGameInputException extends Exception {

    public int lineWithError;
    public String problemDescription;

    public int dadosAMenos=0,dadosAMais=0;

    public InvalidGameInputException(int lineWithError, String problemDescription) {
        this.lineWithError = lineWithError;
        this.problemDescription = problemDescription;
    }

    public int getLineWithError() {
        return lineWithError;
    }


    public String getProblemDescription() {
        return problemDescription;
    }
}
