package pt.ulusofona.lp2.deisichess;

public class Capturas {
    public Peca pecaQueCaptura;
    public Peca pecaCapturada;
    public int nrCapturas;

    public Capturas(Peca pecaQueCaptura, Peca pecaCapturada) {
      this.pecaQueCaptura=pecaQueCaptura;
      this.pecaCapturada=pecaCapturada;
    }

    public Capturas(Peca pecaQueCaptura,int nrCapturas){
        this.pecaQueCaptura=pecaQueCaptura;
        this.nrCapturas=nrCapturas;
    }
    public Peca getPecaQueCaptura() {
        return pecaQueCaptura;
    }
    public Peca getPecaCapturada() {
        return pecaCapturada;
    }

    public int getNrCapturas() {
        return nrCapturas;
    }

    public void incNrCapturas(){
        nrCapturas++;
    }
}
