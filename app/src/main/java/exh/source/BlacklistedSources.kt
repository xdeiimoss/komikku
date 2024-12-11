package exh.source

object BlacklistedSources {
    val EHENTAI_EXT_SOURCES = longArrayOf(
        8100626124886895451,    // E-Hentai (Ja)
        57122881048805941,      // E-Hentai (En)
        4678440076103929247,    // E-Hentai (Zh)
        1876021963378735852,    // E-Hentai (Nl)
        3955189842350477641,    // E-Hentai (Fr)
        4348288691341764259,    // E-Hentai (De)
        773611868725221145,     // E-Hentai (Hu)
        5759417018342755550,    // E-Hentai (It)
        825187715438990384,     // E-Hentai (Ko)
        6116711405602166104,    // E-Hentai (Pl)
        7151438547982231541,    // E-Hentai (PtBr)
        2171445159732592630,    // E-Hentai (Ru)
        3032959619549451093,    // E-Hentai (Es)
        5980349886941016589,    // E-Hentai (Th)
        6073266008352078708,    // E-Hentai (Vi)
        5499077866612745456,    // E-Hentai (None)
        6140480779421365791,    // E-Hentai (Other)
        EH_SOURCE_ID,           // E-Hentai (Multi)
    )

    val BLACKLISTED_EXT_SOURCES = EHENTAI_EXT_SOURCES

    val BLACKLISTED_EXTENSIONS = arrayOf(
        "eu.kanade.tachiyomi.extension.all.ehentai",
    )

    var HIDDEN_SOURCES = setOf(
        MERGED_SOURCE_ID,
    )
}
