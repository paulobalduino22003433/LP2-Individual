package pt.ulusofona.lp2.deisichess;

public class InvalidTeamException extends Exception {
    public String problemDescription;
    public String invalidPieceName;

    public InvalidTeamException(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public String getInvalidPieceName() {
        return invalidPieceName;
    }
}
