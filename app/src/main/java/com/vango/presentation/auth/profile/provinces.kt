package com.vango.presentation.auth.profile

enum class SpanishProvinces {
    ALAVA, ALBACETE, ALICANTE, ALMERIA, ASTURIAS, AVILA,
    BADAJOZ, BARCELONA, BURGOS, CACERES, CADIZ, CANTABRIA,
    CASTELLON, CEUTA, CIUDAD_REAL, CORDOBA, CUENCA, GIRONA,
    GRANADA, GUADALAJARA, GUIPUZCOA, HUELVA, HUESCA, ISLAS_BALEARES,
    JAEN, LA_RIOJA, LAS_PALMAS, LEON, LLEIDA, LUGO, MADRID,
    MALAGA, MELILLA, MURCIA, NAVARRA, OURENSE, PALENCIA,
    PONTEVEDRA, SALAMANCA, SANTA_CRUZ_DE_TENERIFE, SEGOVIA,
    SEVILLA, SORIA, TARRAGONA, TERUEL, TOLEDO, VALENCIA,
    VALLADOLID, VIZCAYA, ZAMORA, ZARAGOZA;

    companion object {
        fun fromString(name: String): SpanishProvinces? {
            return values().find { it.name.equals(name, ignoreCase = true) }
        }
    }
}

enum class PortugueseRegions {
    AVEIRO, BEJA, BRAGA, BRAGANCA, CASTELO_BRANCO, COIMBRA,
    EVORA, FARO, GUARDA, LEIRIA, LISBOA, PORTALEGRE, PORTO,
    SANTAREM, SETUBAL, VIANA_DO_CASTELO, VILA_REAL, VISEU,
    REGIAO_AUTNOMA_DE_AZORES, REGIAO_AUTNOMA_DE_MADEIRA;

    companion object {
        fun fromString(name: String): PortugueseRegions? {
            return values().find { it.name.equals(name, ignoreCase = true) }
        }
    }
}

enum class FrenchRegions {
    ALSACIA, AQUITANIA, AUVERNA_RODANO_ALPES, BRETAA, BORGAA_FRANCO_CONDADO,
    CENTRO_VALLE_DE_LOIRA, CHAMPAGNE_ARDENAS, CORSA, GRAN_ESTE, NORMANDA,
    NUEVA_AQUITANIA, OCCITANIA, PAYS_DE_LA_LOIRE, PROVENZA_ALPES_COTA_AZUL,
    ISLA_DE_FRANCIA, GUYANA_FRANCES, GUADALUPE, MARTINICA, REUNIN, MAYOTTE;

    companion object {
        fun fromString(name: String): FrenchRegions? {
            return values().find { it.name.equals(name, ignoreCase = true) }
        }
    }
}