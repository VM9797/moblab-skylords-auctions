package hu.vm.bme.skylordsauctions.common

import java.lang.RuntimeException

enum class AbilityType(val numericCode: Int) {
    Attack(0),
    Passive(1),
    Activated(2),
    Interval(3),
    Cast(4);

    companion object {
        fun parseInt(value: Int?): AbilityType {
            return when(value){
                0 -> Attack
                1 -> Passive
                2 -> Activated
                3 -> Interval
                4 -> Cast
                else -> throw RuntimeException("Unable to parse ability type value $value")
            }
        }
    }
}