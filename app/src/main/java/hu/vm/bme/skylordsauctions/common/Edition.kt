package hu.vm.bme.skylordsauctions.common

import java.lang.RuntimeException

enum class Edition(val numericCode: Int) {
    Twilight(1),
    Renegade(2),
    LostSouls(4),
    Amii(8);

    companion object {
        fun parseInt(value: Int?): Edition {
            return when(value){
                1 -> Twilight
                2 -> Renegade
                4 -> LostSouls
                8 -> Amii
                else -> throw RuntimeException("Unable to parse edition value $value")
            }
        }
    }
}