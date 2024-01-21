package pt.ulusofona.lp2.deisichess;

public class GameResults {
    public int jogadasSemCaptura;
    public String resultadoJogo = "";

    public GameResults() {}

    public void incJogadasSemCaptura() {
        jogadasSemCaptura++;
    }

    public int getJogadasSemCaptura() {
        return jogadasSemCaptura;
    }

    public String getResultadoJogo() {
        return resultadoJogo;
    }

    public void jogoEmpatado() {
        resultadoJogo = "EMPATE";
    }

    public void pretasGanham() {
        resultadoJogo = "VENCERAM AS PRETAS";
    }

    public void brancasGanham() {
        resultadoJogo = "VENCERAM AS BRANCAS";
    }
}
