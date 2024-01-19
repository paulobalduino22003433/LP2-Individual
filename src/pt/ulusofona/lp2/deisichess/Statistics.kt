package pt.ulusofona.lp2.deisichess


    fun getStatsCalculator(statType: StatType): (GameManager) -> List<String> {
        return when (statType) {
            StatType.TOP_5_CAPTURAS -> { gameManager -> calculateTop5Capturas(gameManager) }
            StatType.TOP_5_PONTOS -> { gameManager -> calculateTop5Pontos(gameManager) }
            StatType.PECAS_MAIS_5_CAPTURAS -> { gameManager -> calculatePecasMais5Capturas(gameManager) }
            StatType.PECAS_MAIS_BARALHADAS -> { gameManager -> calculatePecasMaisBaralhadas(gameManager) }
            StatType.TIPOS_CAPTURADOS -> { gameManager -> calculateTiposCapturados(gameManager) }
        }
    }


    public fun getTeamColor(peca: Peca): String {
    return when (peca.getEquipa()) {
        "10" -> "PRETA"
        "20" -> "BRANCA"
        else -> "UNKNOWN"
    }
    }

     public fun calculateTop5Capturas(gameManager: GameManager): List<String> {
        val capturedPiecesMap = mutableMapOf<String, Pair<Int, String>>()

        for (captura in gameManager.top5Capturas){
            val alcunha = captura.getPecaQueCaptura().getAlcunha()
            val nrCapturas= captura.getNrCapturas()
            val teamColor = getTeamColor(captura.pecaQueCaptura)

            if (capturedPiecesMap.containsKey(alcunha)){
                val currentNrCapturas = capturedPiecesMap[alcunha]!!.first
                capturedPiecesMap[alcunha]=Pair(currentNrCapturas+1,teamColor)
            }else{
                capturedPiecesMap[alcunha] =Pair(nrCapturas,teamColor)
            }
        }

        val sortedEntries = capturedPiecesMap.entries.sortedByDescending { it.value.first }
        val top5Strings = sortedEntries.take(5).map { entry ->
            val alcunha = entry.key
            val (nrCapturas, teamColor) = entry.value
            "$alcunha ($teamColor) fez $nrCapturas capturas"
        }
         return top5Strings
     }

    fun calculateTop5Pontos(gameManager: GameManager): List<String> {
        if (gameManager.capturas.isEmpty()) {
            return emptyList()
        }

        // Map para armazenar informações sobre cada peça capturada
        val capturedPiecesMap = mutableMapOf<String, Pair<Int, String>>() // Pair<Int, String> representa pontos e cores da equipa

        for (captura in gameManager.capturas) {
            val alcunha = captura.getPecaQueCaptura().getAlcunha()
            val pontos = captura.getPecaCapturada().getPontos()
            val teamColor = getTeamColor(captura.getPecaQueCaptura())

            // Checka se a alcunha ja ta no map
            if (capturedPiecesMap.containsKey(alcunha)) {
                //  Update os pontos de uma peca que ja ta no map
                val currentPoints = capturedPiecesMap[alcunha]!!.first
                capturedPiecesMap[alcunha] = Pair(currentPoints + pontos, teamColor)
            } else {
                // Adiciona uma nova peca porque nao existe no map
                capturedPiecesMap[alcunha] = Pair(pontos, teamColor)
            }
        }

        // Sort no map em ordem decrescente com base nos pontos
        val sortedEntries = capturedPiecesMap.entries.sortedByDescending { it.value.first }

        // Tira o top 5 e mete numa string
        val top5Strings = sortedEntries.take(5).map { entry ->
            val alcunha = entry.key
            val (pontos, teamColor) = entry.value
            "$alcunha ($teamColor) tem $pontos pontos"
        }

        return top5Strings
    }


public fun calculatePecasMais5Capturas(gameManager: GameManager): List<String> {
    val capturedPiecesMap = mutableMapOf<String, Pair<Int, String>>()

    for (captura in gameManager.top5Capturas) {
        val alcunha = captura.getPecaQueCaptura().getAlcunha()
        val nrCapturas = captura.getNrCapturas()
        val teamColor = getTeamColor(captura.pecaQueCaptura)

        if (nrCapturas>5) {
            capturedPiecesMap[alcunha] = Pair(nrCapturas, teamColor)
        }
    }


    val sortedEntries = capturedPiecesMap.entries.sortedByDescending { it.value.first }
    val top5Strings = sortedEntries.take(5).map { entry ->
        val alcunha = entry.key
        val (nrCapturas, teamColor) = entry.value
        "$teamColor:$alcunha:$nrCapturas"
    }
    return top5Strings
    }

    public fun calculatePecasMaisBaralhadas(gameManager: GameManager): List<String> {
        return listOf("Result for PECAS_MAIS_BARALHADAS")
    }

public fun calculateTiposCapturados(gameManager: GameManager): List<String> {
    val capturedPiecesSet = mutableSetOf<String>()

    for (captura in gameManager.capturas) {
        val tipoDePeca = captura.getPecaCapturada().tipoDePeca
        val tipoDePecaString = when (tipoDePeca) {
            "0" -> "Rei"
            "1" -> "Rainha"
            "2" -> "Ponei Mágico"
            "3" -> "Padre da Vila"
            "4" -> "TorreHor"
            "5" -> "TorreVert"
            "6" -> "Homer Simpson"
            "7" -> {
                val jokerName = when (GameManager.turnoJoker) {
                    2 -> "Rainha"
                    3 -> "Ponei Mágico"
                    4 -> "Padre da Vila"
                    5 -> "TorreHor"
                    6 -> "TorreVert"
                    7 -> "Homer Simpson"
                    else -> ""
                }
                "Joker/$jokerName"
            }
            else -> ""
        }
        capturedPiecesSet.add(tipoDePecaString)
    }
    return capturedPiecesSet.toList()
}



