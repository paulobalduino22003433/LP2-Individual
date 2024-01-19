package pt.ulusofona.lp2.deisichess;

public class InvalidTeamException {
    public String problemDescription;

    public InvalidTeamException(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getProblemDescription() {
        return problemDescription;
    }
}
