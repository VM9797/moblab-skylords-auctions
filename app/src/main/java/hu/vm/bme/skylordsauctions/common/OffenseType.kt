package hu.vm.bme.skylordsauctions.common

import java.lang.RuntimeException

enum class OffenseType(val numericCode: Int) {
    Small(0),
    Medium(1),
    Large(2),
    XL(3),
    Special(4);


    companion object {
        fun parseInt(value: Int?): OffenseType {
            return when(value){
                0 -> Small
                1 -> Medium
                2 -> Large
                3 -> XL
                4 -> Special
                else -> throw RuntimeException("Unable to parse offense type value $value")
            }
        }
    }
}