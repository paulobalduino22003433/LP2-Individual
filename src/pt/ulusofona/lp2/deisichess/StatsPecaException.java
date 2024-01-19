package pt.ulusofona.lp2.deisichess;

public class StatsPecaException extends Exception {
    String nameException;

    public StatsPecaException(String nameException) {
        super("");
        this.nameException = nameException;
    }

    public boolean isValidMove() {
        return nameException.equals("VALID");
    }

    public boolean isInvalidMove() {
        return nameException.equals("INVALID");
    }

    public boolean isCapture() {
        return nameException.equals("CAPTURE");
    }
}
