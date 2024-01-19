package pt.ulusofona.lp2.deisichess;

public class StatsPeca {
    int captures, validMoves, invalidMoves;

    public StatsPeca() {}

    public void incCaptures() {
        captures++;
    }

    public void incValidMoves() {
        validMoves++;
    }

    public void incInvalidMoves() {
        invalidMoves++;
    }

    public int getCaptures() {
        return captures;
    }

    public int getValidMoves() {
        return validMoves;
    }

    public int getInvalidMoves() {
        return invalidMoves;
    }
}
