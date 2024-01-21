package pt.ulusofona.lp2.deisichess;

import javax.swing.*;
import java.io.*;
import java.util.*;
import pt.ulusofona.lp2.deisichess.PecaJoker;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GameManager {
    public ArrayList<Peca> pecas = new ArrayList<>();
    public static String[][] cordenadasPecasArray;
    public ArrayList<Peca> blackTeam = new ArrayList<>();
    public ArrayList<Peca> whiteTeam = new ArrayList<>();
    public Tabuleiro tabuleiro = new Tabuleiro(whiteTeam, blackTeam);
    public StatsPeca statusPreta = new StatsPeca();
    public StatsPeca statusBranca = new StatsPeca();
    public GameResults gameResults = new GameResults();
    public static int nrTurno=0;

    public static int turnoJoker=1;
    public static boolean savedTurnoEquipa;//loadGame only
    public static int savedNumeroTurno;//loadGame only
    public ArrayList<Capturas> capturas = new ArrayList<>();
    public ArrayList<Capturas> top5Capturas = new ArrayList<>();
    InvalidGameInputException invalidGameInputException = new InvalidGameInputException(0,"");
    public int johnMcClaneCountWhite;
    public int johnMcClaneCountBlack;

    public ArrayList<Peca> yellowTeam = new ArrayList<>();

    public void loadGame(File file) throws IOException, InvalidGameInputException,InvalidTeamException {
        try {
            pecas = new ArrayList<>();
            blackTeam = new ArrayList<>();
            whiteTeam = new ArrayList<>();
            cordenadasPecasArray = null;
            tabuleiro = new Tabuleiro(whiteTeam,blackTeam);
            savedTurnoEquipa = false;
            savedNumeroTurno= -1;
            nrTurno=0;
            turnoJoker=1;
            invalidGameInputException = new InvalidGameInputException(0,"");
            capturas=new ArrayList<>();
            top5Capturas=new ArrayList<>();
            gameResults= new GameResults();
            johnMcClaneCountBlack=0;
            johnMcClaneCountWhite=0;
            yellowTeam = new ArrayList<>();
            tabuleiro.isWhiteVsBlackGame=false;
            tabuleiro.isYellowVsWhiteGame=false;
            tabuleiro.isYellowVsBlackGame=false;


            ArrayList<String> cordenadasPecas = new ArrayList<>();
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String linha;
            int pecasRestantes = 0;

            while ((linha = fileReader.readLine()) != null) {

                if (tabuleiro.getTamanhoTabuleiro() == -1) {
                    tabuleiro.setTamanhoTabuleiro(Integer.parseInt(linha.trim()));
                    tabuleiro.incLinhaDoFicheiro();
                    continue;
                }

                if (tabuleiro.getNumPecaTotal() == -1) {
                    tabuleiro.setNumPecaTotal(Integer.parseInt(linha.trim()));
                    tabuleiro.incLinhaDoFicheiro();
                    continue;
                }

                if (pecasRestantes < tabuleiro.getNumPecaTotal()) {
                    String[] partes = linha.split(":");
                    if (partes.length==4){
                        Peca peca = colocarTipoDePeca(partes[0].trim(), partes[1].trim(), partes[2].trim(), partes[3].trim());
                        if (!partes[2].trim().equals("10") && !partes[2].trim().equals("20") && !partes[2].trim().equals("30")) {
                            throw new InvalidTeamException("Equipa Invalida: " + partes[2].trim());
                        }
                        pecas.add(peca);
                        pecasRestantes++;
                        tabuleiro.incLinhaDoFicheiro();
                        continue;
                    } else if (partes.length < 4) {
                        invalidGameInputException.dadosAMenos= partes.length;
                        throw invalidGameInputException;
                    } else if (partes.length > 4) {
                        invalidGameInputException.dadosAMais = partes.length;
                        throw invalidGameInputException;
                    }
                }

                if (cordenadasPecas.size()<tabuleiro.getTamanhoTabuleiro()){
                    cordenadasPecas.add(linha);
                    tabuleiro.incLinhaDoFicheiro();
                    continue;
                }

                if (!savedTurnoEquipa){
                    if (linha.equals("WhiteTurn")){
                        tabuleiro.isBlackTurn=false;
                        tabuleiro.isWhiteTurn=true;
                    }else {
                        tabuleiro.isWhiteTurn=false;
                        tabuleiro.isBlackTurn=true;
                    }
                    savedTurnoEquipa =true;
                    tabuleiro.incLinhaDoFicheiro();
                    continue;
                }
                if (savedNumeroTurno==-1){
                    nrTurno=Integer.parseInt(linha.trim());
                    savedNumeroTurno=0;
                    tabuleiro.incLinhaDoFicheiro();
                    continue;
                }
                turnoJoker=Integer.parseInt(linha.trim());
            }


            int linhas = tabuleiro.getTamanhoTabuleiro();
            int colunas = tabuleiro.getTamanhoTabuleiro();

            cordenadasPecasArray = new String[linhas][colunas];

            for (int i = 0; i < linhas; i++) {
                String[] parts = cordenadasPecas.get(i).split(":");

                for (int j = 0; j < colunas; j++) {
                    if (j >= 0 && j < parts.length && i >= 0 && i < cordenadasPecasArray.length) {
                        cordenadasPecasArray[i][j] = parts[j];
                    }
                }
            }

            setCoordinatesPieces();
            organizePiece();
            removeCapturedPieces();
            fillTop5Capturas();
            if (yellowTeam.isEmpty()){
                tabuleiro.isYellowTurn=false;
                tabuleiro.isWhiteVsBlackGame=true;
            }
            if (blackTeam.isEmpty()){
                tabuleiro.isYellowVsWhiteGame=true;
            }
            if (whiteTeam.isEmpty()){
                tabuleiro.isYellowTurn=false;
                tabuleiro.isYellowVsBlackGame=true;
            }
            fileReader.close();
        }catch (FileNotFoundException e){
            String errorMessage = "File not found";
            System.err.println(errorMessage);
            e.printStackTrace();
            throw new FileNotFoundException(errorMessage);
        }
        catch (InvalidGameInputException e){
            if (invalidGameInputException.dadosAMenos!=0){
                int linhaDoFicheiro = tabuleiro.getLinhaDoFicheiro();
                String problemDescription = "DADOS A MENOS (Esperava: 4 ; Obtive: " + invalidGameInputException.dadosAMenos + ")";
                InvalidGameInputException invalidGameInputException = new InvalidGameInputException(linhaDoFicheiro,problemDescription);
                System.err.println(problemDescription);
                throw invalidGameInputException;
            }
            int linhaDoFicheiro = tabuleiro.getLinhaDoFicheiro();
            String problemDescription = "DADOS A MAIS (Esperava: 4 ; Obtive: " + invalidGameInputException.dadosAMais + ")";
            InvalidGameInputException invalidGameInputException = new InvalidGameInputException(linhaDoFicheiro,problemDescription);
            System.err.println(problemDescription);
            throw invalidGameInputException;
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidTeamException e) {
            throw new InvalidTeamException("problem description: " + e.getProblemDescription());
        }
    }

    public int getCurrentTeamID() {
        if (tabuleiro.isWhiteVsBlackGame){
            return tabuleiro.getIsBlackTurn() ? 10 : 20;
        }
        if (tabuleiro.isYellowVsBlackGame){
            return tabuleiro.getIsBlackTurn() ? 10 : 30;
        }
        if (tabuleiro.isYellowVsWhiteGame){
            return tabuleiro.getIsYellowTurn() ? 30 : 20;
        }
        return 10;
    }

    public Peca colocarTipoDePeca(String identificador, String tipoDePeca, String equipa, String alcunha) {
        Peca pecaDeRetorno = switch (tipoDePeca) {
            case "0" -> new PecaRei(identificador, tipoDePeca, equipa, alcunha);
            case "1" -> new PecaRainha(identificador, tipoDePeca, equipa, alcunha);
            case "2" -> new PecaPoneiMagico(identificador, tipoDePeca, equipa, alcunha);
            case "3" -> new PecaPadreVila(identificador, tipoDePeca, equipa, alcunha);
            case "4" -> new PecaTorreH(identificador, tipoDePeca, equipa, alcunha);
            case "5" -> new PecaTorreV(identificador, tipoDePeca, equipa, alcunha);
            case "6" -> new PecaHomer(identificador, tipoDePeca, equipa, alcunha);
            case "7" -> new PecaJoker(identificador, tipoDePeca, equipa, alcunha);
            case "10" -> new PecaJohnMcClane(identificador,tipoDePeca,equipa,alcunha);
            default -> null;
        };

        return pecaDeRetorno;
    }

    public void setCoordinatesPieces() {
        for (int y = 0; y < tabuleiro.getTamanhoTabuleiro(); y++) {
            for (int x = 0; x < tabuleiro.getTamanhoTabuleiro(); x++) {
                for (int identificador = 0; identificador < tabuleiro.getNumPecaTotal(); identificador++) {
                    if (pecas.get(identificador).getIdentificador().equals(cordenadasPecasArray[y][x])) {
                        pecas.get(identificador).setX(Integer.toString(x));
                        pecas.get(identificador).setY(Integer.toString(y));
                    }
                }
            }
        }
    }

    public void fillTop5Capturas(){
        for (Peca pecaWhite:whiteTeam){
            Capturas capturaWhite = new Capturas(pecaWhite,0);
            top5Capturas.add(capturaWhite);
        }
        for (Peca pecaBlack:blackTeam){
            Capturas capturaBlack = new Capturas(pecaBlack,0);
            top5Capturas.add(capturaBlack);
        }
    }

    public void organizePiece() {
        for (Peca peca : pecas) {
            if (peca.getEquipa().equals("10")) {
                blackTeam.add(peca);
            }
            if (peca.getEquipa().equals("20")) {
                whiteTeam.add(peca);
            }

            if (peca.getEquipa().equals("30")){
                yellowTeam.add(peca);
            }
        }
    }

    public void removeCapturedPieces() {
        Iterator<Peca> iterator = pecas.iterator();

        while (iterator.hasNext()) {
            Peca peca = iterator.next();
            if (peca.getX().isEmpty() || peca.getY().isEmpty()) {
                peca.estadoPecaCapturado();
                whiteTeam.remove(peca);
                blackTeam.remove(peca);
                yellowTeam.remove(peca);
            }
        }
    }

    public int getBoardSize() {
        return tabuleiro.getTamanhoTabuleiro();
    }


    public String[] getSquareInfo(int x, int y) {
        String[] pecaInfo = new String[5];

        if(x<0 || y<0 || x>tabuleiro.getTamanhoTabuleiro() -1 || y>tabuleiro.getTamanhoTabuleiro() -1) {
            return null;
        }

        for (Peca peca : pecas) {
            if (peca.getX().equals(Integer.toString(x)) && peca.getY().equals(Integer.toString(y))) {
                pecaInfo[0] = peca.getIdentificador();
                pecaInfo[1] = peca.getTipoDePeca();
                pecaInfo[2] = peca.getEquipa();
                pecaInfo[3] = peca.getAlcunha();
                pecaInfo[4] = peca.getPng();
            }
        }

        if (pecaInfo[0] == null) {
            return new String[0];
        }

        return pecaInfo;
    }


    public String[] getPieceInfo(int ID) {
        String[] peca = new String[7];

        for (Peca pecaClone : pecas) {
            if (pecaClone.getIdentificador().equals(Integer.toString(ID))) {
                peca[0] = pecaClone.getIdentificador();
                peca[1] = pecaClone.getTipoDePeca();
                peca[2] = pecaClone.getEquipa();
                peca[3] = pecaClone.getAlcunha();
                peca[4] = pecaClone.getEstado();
                peca[5] = pecaClone.getX();
                peca[6] = pecaClone.getY();
            }
        }

        return peca;
    }


    public String getPieceInfoAsString(int ID) {
        if (ID>=0 && ID<=pecas.size()){
            return pecas.get(ID - 1).toString();
        }
        return "";
    }

    public boolean isMoveValid(Peca peca,int x0,int y0, int x1, int y1) {
      boolean isItvalid = false;
      int percursoHorizontal = x1 - x0;
      int percursoVertical = y1 - y0;
      int jokerMove = 1;

      if (peca.tipoDePeca.equals("10")){
          return false;
      }
      if (peca.tipoDePeca.equals("7")) {
          switch (turnoJoker) {
              case 1:
                  jokerMove = 1;
                  break;
              case 2:
                  jokerMove = 2;
                  break;
              case 3:
                  jokerMove = 3;
                  break;
              case 4:
                  jokerMove = 4;
                  break;
              case 5:
                  jokerMove = 5;
                  break;
              case 6:
                  jokerMove = 6;
                  break;
          }
      }

      if (peca.tipoDePeca.equals("6") || jokerMove == 6) {
          PecaHomer homer = new PecaHomer(peca.identificador, peca.tipoDePeca, peca.equipa, peca.alcunha);
          homer.x = peca.x.trim();
          homer.y = peca.y.trim();

          if (nrTurno % 3 == 0) {
              homer.status = "a dormir";
          } else {
              homer.status ="acordado";
          }

          if (homer.isSleeping()) {
              return false;
          }

          // Checa se o movimento é diagonal e só anda uma casa
          if ((percursoHorizontal == 1 || percursoHorizontal == -1) && (percursoVertical == 1 || percursoVertical == -1)) {
              return true;
          } else {
              return false;
          }
      }

      if (peca.tipoDePeca.equals("2") || jokerMove == 2) {
          if (Math.abs(percursoHorizontal) == 2 && Math.abs(percursoVertical) == 2) {
              return true;
          } else {
              return false;
          }
      }


      if (peca.tipoDePeca.equals("5") || jokerMove == 5) {
          boolean obstacleInPath = false;
          if (x1 == x0) { //Checa se o movimento é vertical
              if (percursoVertical > 0) {
                  for (int y = y0 + 1; y < y1; y++) {
                      if (y >= 0 && y < GameManager.cordenadasPecasArray.length) {
                          if (!Objects.equals(GameManager.cordenadasPecasArray[y][x0], "0")) {
                              obstacleInPath=true;
                              break;
                          }
                      }
                  }
              }
              if (percursoVertical < 0) {
                  for (int y = y1 + 1; y < y0; y++) {
                      if (y >= 0 && y < GameManager.cordenadasPecasArray.length) {
                          if (!Objects.equals(GameManager.cordenadasPecasArray[y][x0], "0")) {
                              obstacleInPath=true;
                              break;
                          }
                      }
                  }
              }
              if (obstacleInPath) {
                  return false;
              }
              return true;
          } else {
              return false;
          }
      }

      if (peca.tipoDePeca.equals("3") || jokerMove == 3) {
          //Checa se o movimento é diagonal
          if (percursoHorizontal == percursoVertical || percursoHorizontal == -percursoVertical) {
              //Checa se o movimento não passa de 3 casas
              if (percursoHorizontal <= 3 && percursoHorizontal >= -3) {
                  //Itera sobre as casas entre (x0, y0) e (x1, y1)
                  for (int i = 1; i < Math.abs(percursoHorizontal); i++) {
                      int checkX = x0 + Integer.signum(percursoHorizontal) * i;
                      int checkY = y0 + Integer.signum(percursoVertical) * i;

                      //Checa se existe um obstaculo no caminho
                      if (!cordenadasPecasArray[checkY][checkX].equals("0")) {
                          return false; //Movimento bloqueado por obstaculo
                      }
                  }
                  return true; //Movimento permitido
              }
          }
          return false;
      }
      if (peca.tipoDePeca.equals("0")) {
          if (x1 == x0) { //movimento vertical
              if ((y1 - y0 == 1) || (y1 - y0 == -1)) {
                  return true;
              }
          }
          if (y1 == y0) { //movimento horizontal
              if ((x1 - x0 == 1 || x1 - x0 == -1)) {
                  return true;
              }
          }

          if ((x1 == x0 + 1 || x1 == x0 - 1) && (y1 == y0 + 1 || y1 == y0 - 1)) { //movimento diagonal
              return true;
          }

          return false;
      }

        if (peca.tipoDePeca.equals("4") || jokerMove == 4) {
            if (y1 == y0) { //Checka se o movimento é horizontal
                boolean obstacleInPath = false;

                if (percursoHorizontal > 0) {
                    for (int x = x0 + 1; x < x1; x++) {
                        if (x >= 0 && x < cordenadasPecasArray.length) {
                            if (!Objects.equals(cordenadasPecasArray[y0][x], "0")) {
                                obstacleInPath = true;
                                break;
                            }
                        }
                    }
                }
                if (percursoHorizontal < 0) {
                    for (int x = x1 + 1; x < x0; x++) {
                        if (x >= 0 && x < cordenadasPecasArray.length) {
                            if (!Objects.equals(cordenadasPecasArray[y0][x], "0")) {
                                obstacleInPath = true;
                                break;
                            }
                        }
                    }
                }

                if (obstacleInPath) {
                    return false;
                }

                return true;
            } else{
                return false;
            }
        }

      if (peca.tipoDePeca.equals("1") || jokerMove == 1) {
          boolean valid = false;
          boolean isHorizontal=false;
          boolean isVertical=false;
          boolean isDiagonal=false;
          // Checka se o movimento é horizontal
          if (percursoVertical == 0 && (percursoHorizontal >= -5 && percursoHorizontal <= 5)) {
              valid= true;
              isHorizontal=true;
          }

          // Checka se o movimento é vertical
          if (percursoHorizontal == 0 && (percursoVertical >= -5 && percursoVertical <= 5)) {
              valid= true;
              isVertical=true;
          }

          // Checka se o movimento é diagonal
          if ((percursoHorizontal == percursoVertical || percursoHorizontal == -percursoVertical) &&
                  (percursoHorizontal >= -5 && percursoHorizontal <= 5)) {
              valid= true;
              isDiagonal=true;
          }
          if (valid){
              if (x1 >= 0 && x1 < cordenadasPecasArray[y0].length && y1 >= 0 && y1 < cordenadasPecasArray.length) {
                  if (tabuleiro.isBlackTurn){
                      for (Peca pecaWhite:whiteTeam){
                          if (pecaWhite.getIdentificador().equals(cordenadasPecasArray[y1][x1])){
                              if (pecaWhite.tipoDePeca.equals("1") || pecaWhite.toString().contains("Joker/Rainha")){
                                  return false;
                              }
                          }
                      }
                  }
              }else{
                  return false;
              }
              if (y1 >= 0 && y1 < cordenadasPecasArray.length && x1 >= 0 && x1 < cordenadasPecasArray[y1].length) {
                  if (tabuleiro.isWhiteTurn){
                      for (Peca pecaBlack:blackTeam){
                          if (pecaBlack.getIdentificador().equals(cordenadasPecasArray[y1][x1])){
                              if (pecaBlack.tipoDePeca.equals("1") || pecaBlack.toString().contains("Joker/Rainha")){
                                  return false;
                              }
                          }
                      }
                  }
              }else{
                  return false;
              }

              if (isHorizontal){
                  // Itera sobre as casas entre (x0, y0) e (x1, y1) para movimento horizontal
                  for (int i = 1; i < Math.abs(percursoHorizontal); i++) {
                      int checkX = x0 + Integer.signum(percursoHorizontal) * i;
                      int checkY = y0;

                      //Checa se existe um obstaculo na casa de momento
                      if (!cordenadasPecasArray[checkY][checkX].equals("0")) {
                          return false; // Movimento bloqueado por obstaculo
                      }
                  }
                  return true; // Movimento horizontal permitido
              }

              if (isVertical){
                  // Itera sobre as casas entre (x0, y0) e (x1, y1) para movimento vertical
                  for (int i = 1; i < Math.abs(percursoVertical); i++) {
                      int checkX = x0;
                      int checkY = y0 + Integer.signum(percursoVertical) * i;

                      // Checa se existe um obstaculo na casa de momento
                      if (!cordenadasPecasArray[checkY][checkX].equals("0")) {
                          return false; // Movimento bloqueado por obstaculo
                      }
                  }
                  return true; // Movimento vertical permitido
              }
              if (isDiagonal){
                  // Itera sobre as casas entre (x0, y0) e (x1, y1) para movimento diagonal
                  for (int i = 1; i < Math.abs(percursoHorizontal); i++) {
                      int checkX = x0 + Integer.signum(percursoHorizontal) * i;
                      int checkY = y0 + Integer.signum(percursoVertical) * i;

                      // Checa se existe um obstaculo na casa de momento
                      if (!cordenadasPecasArray[checkY][checkX].equals("0")) {
                          return false; // Movimento bloqueado por obstaculo
                      }
                  }
                  return true;
              }

             return false;
          }
      }

        return isItvalid;

    }

    public boolean move(int x0, int y0, int x1, int y1) {

        boolean wasMoveValid = false;
        Peca pecaParaMover = null;

        if (tabuleiro.getIsWhiteTurn()) {
            for (Peca peca : whiteTeam) {
                if (peca.getIdentificador().equals(cordenadasPecasArray[y0][x0])) {
                    pecaParaMover = peca;
                    break;
                }
            }
        }

        if (tabuleiro.getIsBlackTurn()) {
            for (Peca peca : blackTeam) {
                if (peca.getIdentificador().equals(cordenadasPecasArray[y0][x0])) {
                    pecaParaMover = peca;
                    break;
                }
            }
        }

        if (tabuleiro.getIsYellowTurn()){
            for (Peca peca: yellowTeam){
                if (peca.getIdentificador().equals(cordenadasPecasArray[y0][x0])){
                    pecaParaMover=peca;
                    break;
                }
            }
        }

        if (pecaParaMover!=null){
            if(isMoveValid(pecaParaMover, x0, y0, x1, y1)){
                wasMoveValid=true;
            }
        }

        if (!wasMoveValid){
            if(tabuleiro.getIsBlackTurn()){
                statusPreta.incInvalidMoves();
                return false;
            }
            statusBranca.incInvalidMoves();
            return false;
        }


        if (x1 < 0 || y1 < 0) {
            if(tabuleiro.getIsBlackTurn()){
                statusPreta.incInvalidMoves();
                return false;
            }
            statusBranca.incInvalidMoves();
            return false;
        }

        if (x1 > tabuleiro.getTamanhoTabuleiro() - 1 || y1 > tabuleiro.getTamanhoTabuleiro() - 1) {
            if(tabuleiro.getIsBlackTurn()){
                statusPreta.incInvalidMoves();
                return false;
            }
            statusBranca.incInvalidMoves();
            return false;
        }

        if (x0 == x1 && y0 == y1) {
            if(tabuleiro.getIsBlackTurn()){
                statusPreta.incInvalidMoves();
                return false;
            }
            statusBranca.incInvalidMoves();
            return false;
        }

        if (cordenadasPecasArray[y0][x0].equals("0")) {
            if(tabuleiro.getIsBlackTurn()){
                statusPreta.incInvalidMoves();
                return false;
            }
            statusBranca.incInvalidMoves();
            return false;
        }


        String pecaAtual = cordenadasPecasArray[y0][x0];
        String movimentoParaPeca = cordenadasPecasArray[y1][x1];

        boolean pecaCapturadaAgora = false;

        if (tabuleiro.getIsBlackTurn()) {
            Peca pecaQueCaptura = null;
            Peca pecaCapturada =  null;
            for(Peca peca: blackTeam){
                if (peca.getIdentificador().equals(movimentoParaPeca)){
                    statusPreta.incInvalidMoves();
                    return false;
                }
            }
            for (Peca pecaBranca : whiteTeam) {
                if (pecaBranca.getIdentificador().equals(pecaAtual)) {
                    statusPreta.incInvalidMoves();
                    return false;
                }
            }

            for (Peca peca:blackTeam){
                if (peca.getIdentificador().equals(pecaAtual)){
                    pecaQueCaptura=peca;
                    break;
                }
            }

            for (Peca peca:whiteTeam){
                if (peca.getIdentificador().equals(movimentoParaPeca)){
                    pecaCapturada=peca;
                    break;
                }
            }
            boolean isInTop5=false;
            for (Peca pecaBranca : whiteTeam) {
                if (pecaBranca.getIdentificador().equals(movimentoParaPeca)) {
                    if (pecaQueCaptura!=null && pecaCapturada != null){
                        Capturas captura = new Capturas(pecaQueCaptura,pecaCapturada);
                        capturas.add(captura);
                        for (Capturas capturas1:top5Capturas){
                            if (capturas1.pecaQueCaptura.getIdentificador().equals(pecaAtual)){
                                capturas1.incNrCapturas();
                                isInTop5=true;
                                break;
                            }
                        }
                        if (!isInTop5){
                           Capturas captura1 = new Capturas(pecaQueCaptura,1);
                           top5Capturas.add(captura1);
                        }
                    }
                    if (pecaBranca.tipoDePeca.equals("10")){
                        if (johnMcClaneCountBlack<3){
                            cordenadasPecasArray[y0][x0] = movimentoParaPeca;
                            cordenadasPecasArray[y1][x1] = pecaAtual;
                            for (Peca pecaTemporaria : pecas) {
                                if (pecaTemporaria.getIdentificador().equals(pecaAtual)) {
                                    pecaTemporaria.setX(Integer.toString(x1));
                                    pecaTemporaria.setY(Integer.toString(y1));
                                }
                                if (pecaTemporaria.getIdentificador().equals(movimentoParaPeca)){
                                    pecaTemporaria.setX(Integer.toString(x0));
                                    pecaTemporaria.setY(Integer.toString(y0));
                                }
                            }
                            if (turnoJoker==6){
                                turnoJoker=0;
                            }
                            turnoJoker++;
                            nrTurno++;
                            johnMcClaneCountBlack++;
                            tabuleiro.changeTurnInGame();
                            return true;
                        }
                    }
                    pecaBranca.estadoPecaCapturado();
                    pecaBranca.x = "";
                    pecaBranca.y = "";
                    whiteTeam.remove(pecaBranca);
                    pecaCapturadaAgora = true;
                    statusPreta.incCaptures();
                    gameResults.jogadasSemCaptura=0;
                    break;
                }
            }
            statusPreta.incValidMoves();
        }
        if (tabuleiro.getIsWhiteTurn()) {
            Peca pecaQueCaptura = null;
            Peca pecaCapturada =  null;
            for(Peca peca:whiteTeam){
                if(peca.getIdentificador().equals(movimentoParaPeca)){
                    statusBranca.incInvalidMoves();
                    return false;
                }
            }
            for (Peca pecaPreta : blackTeam) {
                if (pecaPreta.getIdentificador().equals(pecaAtual)) {
                    statusBranca.incInvalidMoves();
                    return false;
                }
            }
            for (Peca peca:whiteTeam){
                if (peca.getIdentificador().equals(pecaAtual)){
                    pecaQueCaptura=peca;
                    break;
                }
            }
            for (Peca peca:blackTeam){
                if (peca.getIdentificador().equals(movimentoParaPeca)){
                    pecaCapturada=peca;
                    break;
                }
            }

            boolean isInTop5=false;
            for (Peca pecaPreta : blackTeam) {
                if (pecaPreta.getIdentificador().equals(movimentoParaPeca)) {
                    if (pecaQueCaptura!=null && pecaCapturada != null){
                        Capturas captura = new Capturas(pecaQueCaptura,pecaCapturada);
                        capturas.add(captura);
                        for (Capturas capturas1:top5Capturas){
                            if (capturas1.pecaQueCaptura.getIdentificador().equals(pecaAtual)){
                                capturas1.incNrCapturas();
                                isInTop5=true;
                                break;
                            }
                        }
                        if (!isInTop5){
                            Capturas captura1 = new Capturas(pecaQueCaptura,1);
                            top5Capturas.add(captura1);
                        }
                    }

                    if (pecaPreta.tipoDePeca.equals("10")){
                        if (johnMcClaneCountWhite<3){

                            cordenadasPecasArray[y0][x0] = movimentoParaPeca;
                            cordenadasPecasArray[y1][x1] = pecaAtual;

                            for (Peca pecaTemporaria : pecas) {
                                if (pecaTemporaria.getIdentificador().equals(pecaAtual)) {
                                    pecaTemporaria.setX(Integer.toString(x1));
                                    pecaTemporaria.setY(Integer.toString(y1));
                                }
                                if (pecaTemporaria.getIdentificador().equals(movimentoParaPeca)){
                                    pecaTemporaria.setX(Integer.toString(x0));
                                    pecaTemporaria.setY(Integer.toString(y0));
                                }
                            }
                            if (turnoJoker==6){
                                turnoJoker=0;
                            }
                            turnoJoker++;
                            nrTurno++;
                            johnMcClaneCountWhite++;
                            tabuleiro.changeTurnInGame();
                            return true;
                        }
                    }
                    pecaPreta.estadoPecaCapturado();
                    pecaPreta.x = "";
                    pecaPreta.y = "";
                    blackTeam.remove(pecaPreta);
                    pecaCapturadaAgora = true;
                    statusBranca.incCaptures();
                    gameResults.jogadasSemCaptura=0;
                    break;
                }
            }
            statusBranca.incValidMoves();
        }

        if (pecaCapturadaAgora) {
            tabuleiro.umaPecaMorreu();
        }

        if (tabuleiro.algumaPecaMorreu() && !pecaCapturadaAgora) {
            gameResults.incJogadasSemCaptura();
        }

        cordenadasPecasArray[y0][x0] = "0";
        cordenadasPecasArray[y1][x1] = pecaAtual;

        for (Peca pecaTemporaria : pecas) {
            if (pecaTemporaria.getIdentificador().equals(pecaAtual)) {
                pecaTemporaria.setX(Integer.toString(x1));
                pecaTemporaria.setY(Integer.toString(y1));
            }
        }

        if (turnoJoker==6){
            turnoJoker=0;
        }
        turnoJoker++;
        nrTurno++;
        tabuleiro.changeTurnInGame();
        return true;
    }


    public boolean gameOver() {
        boolean isThereWhiteKing=false;
        boolean isThereBlackKing=false;
        boolean isThereYellowKing=false;

        if (tabuleiro.isWhiteVsBlackGame){
            for (Peca pecaBlack:blackTeam){
                if (pecaBlack.tipoDePeca.equals("0")){
                    isThereBlackKing=true;
                }
            }

            for (Peca pecaWhite:whiteTeam){
                if (pecaWhite.tipoDePeca.equals("0")){
                    isThereWhiteKing=true;
                }
            }

            if (isThereBlackKing && !isThereWhiteKing){
                gameResults.pretasGanham();
                return true;
            }

            if (isThereWhiteKing && !isThereBlackKing){
                gameResults.brancasGanham();
                return true;
            }

            if ((isThereBlackKing && isThereWhiteKing && blackTeam.size()==1 && whiteTeam.size()==1) || (gameResults.getJogadasSemCaptura()>=10 && tabuleiro.algumaPecaMorreu())) {
                gameResults.jogoEmpatado();
                return true;
            }
        }
        if (tabuleiro.isYellowVsBlackGame){
            for (Peca pecaBlack:blackTeam){
                if (pecaBlack.tipoDePeca.equals("0")){
                    isThereBlackKing=true;
                }
            }

            for (Peca pecaYellow:yellowTeam){
                if (pecaYellow.tipoDePeca.equals("0")){
                    isThereYellowKing=true;
                }
            }

            if (isThereBlackKing && !isThereYellowKing){
                gameResults.pretasGanham();
                return true;
            }

            if (isThereYellowKing && !isThereBlackKing){
                gameResults.amarelasGanham();
                return true;
            }

            if ((isThereBlackKing && isThereYellowKing && blackTeam.size()==1 && yellowTeam.size()==1) || (gameResults.getJogadasSemCaptura()>=10 && tabuleiro.algumaPecaMorreu())) {
                gameResults.jogoEmpatado();
                return true;
            }

        }
        if(tabuleiro.isYellowVsWhiteGame){
            for (Peca pecaWhite:whiteTeam){
                if (pecaWhite.tipoDePeca.equals("0")){
                    isThereWhiteKing=true;
                }
            }

            for (Peca pecaYellow:yellowTeam){
                if (pecaYellow.tipoDePeca.equals("0")){
                    isThereYellowKing=true;
                }
            }

            if (isThereWhiteKing && !isThereYellowKing){
                gameResults.brancasGanham();
                return true;
            }

            if (isThereYellowKing && !isThereWhiteKing){
                gameResults.amarelasGanham();
                return true;
            }

            if ((isThereWhiteKing && isThereYellowKing && whiteTeam.size()==1 && yellowTeam.size()==1) || (gameResults.getJogadasSemCaptura()>=10 && tabuleiro.algumaPecaMorreu())) {
                gameResults.jogoEmpatado();
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getGameResults() {
        ArrayList<String> placar = new ArrayList<>();

        placar.add("JOGO DE CRAZY CHESS");
        placar.add("Resultado: " + gameResults.getResultadoJogo());
        placar.add("---");
        placar.add("Equipa das Pretas");
        placar.add(Integer.toString(statusPreta.getCaptures()));
        placar.add(Integer.toString(statusPreta.getValidMoves()));
        placar.add(Integer.toString(statusPreta.getInvalidMoves()));
        placar.add("Equipa das Brancas");
        placar.add(Integer.toString(statusBranca.getCaptures()));
        placar.add(Integer.toString(statusBranca.getValidMoves()));
        placar.add(Integer.toString(statusBranca.getInvalidMoves()));
        return placar;
    }



    public void saveGame(File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Salvar tamanho do tabuleiro
            writer.write(tabuleiro.getTamanhoTabuleiro() + "\n");

            // Salvar numero de pecas
            writer.write(tabuleiro.getNumPecaTotal() + "\n");

            // Salvar detalhe das pecas
            for (Peca peca : pecas) {
                String pieceDetails = peca.getIdentificador() + ":" + peca.getTipoDePeca() + ":"
                        + peca.getEquipa() + ":" + peca.getAlcunha() + "\n";
                writer.write(pieceDetails);
            }

            // Salvar posicao atual das pecas
            for (int i = 0; i < cordenadasPecasArray.length; i++) {
                String linha = "";
                for (int j = 0; j < cordenadasPecasArray[i].length; j++) {
                    if (i >= 0 && i < cordenadasPecasArray.length && j >= 0 && j < cordenadasPecasArray[i].length) {
                        linha += cordenadasPecasArray[i][j];
                        if (j < cordenadasPecasArray[i].length - 1) {
                            linha += ":";
                        }
                    } else {
                        linha += "";
                    }
                }
                writer.write(linha + "\n");
            }

            if (tabuleiro.getIsBlackTurn()){
                writer.write("BlackTurn" + "\n" );
            }else{
                writer.write("WhiteTurn"+ "\n");
            }

            writer.write(nrTurno + "\n");
            writer.write(turnoJoker + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void undo() {
    }


    public List<Comparable> getHints(int x, int y) {
        List<Comparable> hints = new ArrayList<>();
        boolean skip =false;

        Peca currentPiece = Peca.getPecaByCoordinates(x, y, pecas);

        if (currentPiece != null) {
            if(currentPiece.tipoDePeca.equals("10")){
                hints.add("Sou o John McClane. Yippee ki yay. Sou duro de roer, mas não me sei mover");
                return hints;
            }
            for (int i = 0; i < cordenadasPecasArray.length; i++) {
                for (int j = 0; j < cordenadasPecasArray[i].length; j++) {
                    skip=false;
                    if (tabuleiro.getIsBlackTurn()) {
                        for(Peca peca: blackTeam){
                            if (peca.getIdentificador().equals(cordenadasPecasArray[j][i])){
                                skip=true;
                                break;
                            }
                        }
                    }
                    if (tabuleiro.getIsWhiteTurn()) {
                        for(Peca peca: whiteTeam){
                            if (peca.getIdentificador().equals(cordenadasPecasArray[j][i])){
                                skip=true;
                                break;
                            }
                        }
                    }
                    if (skip){
                        continue;
                    }
                      if (skip){
                        continue;
                    }
                    if (isMoveValid(currentPiece, x, y, i, j)) {
                        Peca targetPiece = Peca.getPecaByCoordinates(i, j, pecas);
                        int points = (targetPiece != null) ? targetPiece.getPontos() : 0;
                        hints.add(new Hint(i, j, points));
                    }
                }
            }
        }

        // Sort hints in descending order
        Collections.sort(hints, Collections.reverseOrder());

        return hints;
    }




    public JPanel getAuthorsPanel() {
        JPanel authorsPanel = new JPanel();

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/images/two-face.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image != null) {
            ImageIcon imageIcon = new ImageIcon(image);
            JLabel label = new JLabel(imageIcon);
            authorsPanel.add(label);
        } else {
            JLabel errorLabel = new JLabel("Erro a carregar imagem");
            authorsPanel.add(errorLabel);
        }

        return authorsPanel;
    }

    public Map<String,String> customizeBoard(){
       HashMap<String,String> map = new HashMap<>();
       return map;
    }

}