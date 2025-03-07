package com.vango.presentation.auth.profile

enum class ForbiddenWords {
    // --- Palabras originales ---
    MIERDA,
    PUTA,
    CULO,
    COÑO,
    VERGA,
    CHINGAR,
    MARICON,
    PERRA,
    GILIPOYAS,
    CABRON,
    PUTO,
    MAMON,
    CAGADA,
    JOTO,
    NACO,
    GONORREA,
    ZORRA,
    HIJO_DE_PUTA,
    CHINGADA,
    PINCHE,
    ESTUPIDO,
    MALPARIDO,
    HUEVON,
    MIERDERO,
    FORRO,
    RATA,
    SANGRE,
    TORTA,
    WEY,
    BOLO,
    CAGON,
    PEDO,
    PENDAJO,
    IMBECIL,
    BOBO,
    TARADO,
    RETRASADO,
    MONGO,
    SUBNORMAL,
    LAMECULOS,
    MALDITO,
    DESGRACIADO,
    CRETINO,
    IDIOTA,
    BOLUDO,
    HDP,
    ANIMAL,
    BASURA,
    CHULO,
    GARRULO,
    MENDRUGO,
    PALETO,
    PATAN,
    PENE,
    RUFIAN,
    SINVERGUENZA,
    TONTO,
    ZOPILote,

    // --- Combinaciones con números ---
    M13RD4,   // MIERDA
    PVT4,     // PUTA
    C0L0,     // CULO
    C0N0,     // COÑO (reemplazando Ñ por N)
    C0Ñ0,
    V3RG4,    // VERGA
    CH1NG4R,  // CHINGAR
    M4R1C0N,  // MARICON
    P3RR4,    // PERRA
    G1L1P0Y4S, // GILIPOYAS
    C4BR0N,   // CABRON
    PVT0,     // PUTO
    M4M0N,    // MAMON
    C4G4D4,   // CAGADA
    J0T0,     // JOTO
    N4C0,     // NACO
    G0N0RR34, // GONORREA
    Z0RR4,    // ZORRA
    H1J0D3PVT4, // HIJO_DE_PUTA
    CH1NG4D4, // CHINGADA
    P1NCH3,   // PINCHE
    STUP1D0, // ESTUPIDO
    M4LP4R1D0, // MALPARIDO
    HV3V0N,   // HUEVON
    M13RD3R0, // MIERDERO
    F0RR0,    // FORRO
    R4T4,     // RATA
    S4NGR3,   // SANGRE
    T0RT4,    // TORTA
    W3Y,      // WEY
    B0L0,     // BOLO
    C4G0N,    // CAGON
    P3D0,     // PEDO
    P3ND4J0,  // PENDAJO
    MB3C1L,  // IMBECIL
    B0B0,     // BOBO
    T4R4D0,   // TARADO
    R3TR4S4D0, // RETRASADO
    M0NG0,    // MONGO
    S0BN0RM4L, // SUBNORMAL
    L4M3CUL0S, // LAMECULOS
    M4LD1T0,  // MALDITO
    D3SGR4C14D0, // DESGRACIADO
    CR3T1N0,  // CRETINO
    D10T4,   // IDIOTA
    B0LUD0,   // BOLUDO
    HD9,      // HDP (P=9)
    N1M4L,   // ANIMAL
    B4S0R4,   // BASURA
    CHUL0,    // CHULO
    G4RRUL0,  // GARRULO
    M3NDRUG0, // MENDRUGO
    P4L3T0,   // PALETO
    P4T4N,    // PATAN
    P3N3,     // PENE
    PUT4,
    RUF14N,   // RUFIAN
    S1NV3RGU3NZ4, // SINVERGUENZA
    T0NT0,    // TONTO
    Z0P1L0T3; // ZOPILote

    companion object {
        fun fromString(name: String): ForbiddenWords? {
            return values().find { it.name.equals(name, ignoreCase = true) }
        }
    }
}
