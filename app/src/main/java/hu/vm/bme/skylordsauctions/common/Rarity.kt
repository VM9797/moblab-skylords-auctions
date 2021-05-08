package hu.vm.bme.skylordsauctions.common

import java.lang.RuntimeException

enum class Rarity(val rarityCode: Int) {
    Common(0),
    Uncommon(1),
    Rare(2),
    UltraRare(3),
    Promo(4),
    Santa(5);

    companion object {
        fun parseInt(value: Int?): Rarity {
            return when(value){
                0 -> Common
                1 -> Uncommon
                2 -> Rare
                3 -> UltraRare
                4 -> Promo
                5 -> Santa
                else -> throw RuntimeException("Unable to parse rarity type value $value")
            }
        }
    }
}