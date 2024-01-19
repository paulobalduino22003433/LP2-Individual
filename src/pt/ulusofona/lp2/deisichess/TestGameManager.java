package pt.ulusofona.lp2.deisichess;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGameManager {

    @Test
    void testloadGame(){
        GameManager gameManager = new GameManager();
        try {
        gameManager.loadGame(new File("test-files/4x4.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidGameInputException e) {
            throw new RuntimeException(e);
        }
    }


        @Test
        void testHomer() throws IOException, InvalidGameInputException {
            GameManager gameManager = new GameManager();
            gameManager.loadGame(new File("test-files/8x8.txt"));
            int x0 = 6, y0 = 0, x1 = 5, y1 =1;
            int x2 = 6, y2 = 7, x3 = 5, y3 =6;
            int x4 = 3, y4 = 4, x5 = 2, y5 =3;
            Peca pecaParaMover = new Peca("7", "6", "10", "Hommie");
            GameManager.nrTurno = 2;
            assertEquals(true,gameManager.isMoveValid(pecaParaMover,x0,y0,x1,y1));
            assertEquals(true,gameManager.isMoveValid(pecaParaMover,x2,y2,x3,y3));
            assertEquals(true,gameManager.isMoveValid(pecaParaMover,x4,y4,x5,y5));
            GameManager.nrTurno = 3;
            assertEquals(false,gameManager.isMoveValid(pecaParaMover,x0,y0,x1,y1));
            assertEquals(false,gameManager.isMoveValid(pecaParaMover,x2,y2,x3,y3));
            assertEquals(false,gameManager.isMoveValid(pecaParaMover,x4,y4,x5,y5));
            PecaHomer homer = new PecaHomer(pecaParaMover.identificador,pecaParaMover.tipoDePeca,pecaParaMover.equipa,pecaParaMover.alcunha);
            assertEquals(true,homer.isSleeping());
        }

        @Test
        void testToStringHomer() throws IOException, InvalidGameInputException {
        GameManager gameManager = new GameManager();
        gameManager.loadGame(new File("test-files/8x8.txt"));
        GameManager.nrTurno=6;
            Peca pecaParaMover = new Peca("7", "6", "10", "Hommie");
            PecaHomer homer = new PecaHomer(pecaParaMover.identificador,pecaParaMover.tipoDePeca,pecaParaMover.equipa,pecaParaMover.alcunha);
        assertEquals("Doh! zzzzzz",homer.toString());
        GameManager.nrTurno=8;
        gameManager.setCoordinatesPieces();
            if (gameManager.tabuleiro.getIsBlackTurn()) {
                for (Peca peca : gameManager.blackTeam) {
                    if (peca.getIdentificador().equals(pecaParaMover.identificador)) {
                        pecaParaMover=peca;
                        pecaParaMover.x=peca.x;
                        pecaParaMover.y=peca.y;
                        homer = new PecaHomer(pecaParaMover.identificador,pecaParaMover.tipoDePeca,pecaParaMover.equipa,pecaParaMover.alcunha);
                        homer.x=pecaParaMover.x;
                        homer.y=pecaParaMover.y;
                    }
                }
            }
            assertEquals("7 | Homer Simpson | 2 | 10 | Hommie @ (6, 0)",homer.toString());
        }

}
