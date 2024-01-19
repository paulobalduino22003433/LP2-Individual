package pt.ulusofona.lp2.deisichess;

public class Hint implements Comparable<Hint> {
    public int x;
    public int y;
    public int points;

    public Hint(int x, int y, int points) {
        this.x = x;
        this.y = y;
        this.points = points;
    }

    @Override
    public int compareTo(Hint other) {
        // Reverte a ordem do sort dos pontos no toString para tar de maior pontos para menores pontos
        return Integer.compare(other.points, this.points);
    }


    @Override
    public String toString() {
        return "(" + x + "," + y + ") -> " + points;
    }
}
