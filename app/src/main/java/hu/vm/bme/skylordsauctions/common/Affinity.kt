package hu.vm.bme.skylordsauctions.common

import java.lang.RuntimeException

enum class Affinity(val numericCode: Int) {
    None(-1),
    Frost(0),
    Fire(1),
    Nature(2),
    Shadow(3);

    companion object {
        fun parseInt(value: Int?): Affinity {
            return when(value){
                -1 -> None
                0 -> Frost
                1 -> Fire
                2 -> Nature
                3 -> Shadow
                else -> throw RuntimeException("Unable to parse affinity value $value")
            }
        }
    }
}