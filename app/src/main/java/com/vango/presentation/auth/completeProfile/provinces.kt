package com.vango.presentation.auth.completeProfile

enum class SpanishProvinces(val id: Int) {
    ALAVA(1), ALBACETE(2), ALICANTE(3), ALMERIA(4), ASTURIAS(5), AVILA(6),
    BADAJOZ(7), BARCELONA(8), BURGOS(9), CACERES(10), CADIZ(11), CANTABRIA(12),
    CASTELLON(13), CEUTA(14), CIUDAD_REAL(15), CORDOBA(16), CUENCA(17), GIRONA(18),
    GRANADA(19), GUADALAJARA(20), GUIPUZCOA(21), HUELVA(22), HUESCA(23), ISLAS_BALEARES(24),
    JAEN(25), LA_RIOJA(26), LAS_PALMAS(27), LEON(28), LLEIDA(29), LUGO(30), MADRID(31),
    MALAGA(32), MELILLA(33), MURCIA(34), NAVARRA(35), OURENSE(36), PALENCIA(37),
    PONTEVEDRA(38), SALAMANCA(39), SANTA_CRUZ_DE_TENERIFE(40), SEGOVIA(41),
    SEVILLA(42), SORIA(43), TARRAGONA(44), TERUEL(45), TOLEDO(46), VALENCIA(47),
    VALLADOLID(48), VIZCAYA(49), ZAMORA(50), ZARAGOZA(51);

    companion object {
        fun fromString(name: String): SpanishProvinces? {
            return values().find { it.name.equals(name, ignoreCase = true) }
        }

        fun fromId(id: Int): SpanishProvinces? {
            return values().find { it.id == id }
        }
    }
}

enum class PortugueseRegions(val id: Int) {
    AVEIRO(1), BEJA(2), BRAGA(3), BRAGANCA(4), CASTELO_BRANCO(5), COIMBRA(6),
    EVORA(7), FARO(8), GUARDA(9), LEIRIA(10), LISBOA(11), PORTALEGRE(12), PORTO(13),
    SANTAREM(14), SETUBAL(15), VIANA_DO_CASTELO(16), VILA_REAL(17), VISEU(18),
    REGIAO_AUTNOMA_DE_AZORES(19), REGIAO_AUTNOMA_DE_MADEIRA(20);

    companion object {
        fun fromString(name: String): PortugueseRegions? {
            return values().find { it.name.equals(name, ignoreCase = true) }
        }

        fun fromId(id: Int): PortugueseRegions? {
            return values().find { it.id == id }
        }
    }
}

enum class FrenchRegions(val id: Int) {
    ALSACIA(1), AQUITANIA(2), AUVERNA_RODANO_ALPES(3), BRETAA(4), BORGAA_FRANCO_CONDADO(5),
    CENTRO_VALLE_DE_LOIRA(6), CHAMPAGNE_ARDENAS(7), CORSA(8), GRAN_ESTE(9), NORMANDA(10),
    NUEVA_AQUITANIA(11), OCCITANIA(12), PAYS_DE_LA_LOIRE(13), PROVENZA_ALPES_COTA_AZUL(14),
    ISLA_DE_FRANCIA(15), GUYANA_FRANCES(16), GUADALUPE(17), MARTINICA(18), REUNIN(19), MAYOTTE(20);

    companion object {
        fun fromString(name: String): FrenchRegions? {
            return values().find { it.name.equals(name, ignoreCase = true) }
        }

        fun fromId(id: Int): FrenchRegions? {
            return values().find { it.id == id }
        }
    }
}