package hu.vm.bme.skylordsauctions.common

import java.lang.RuntimeException

enum class DefenseType(val numericCode: Int) {
    Small(0),
    Medium(1),
    Large(2),
    XL(3),
    Building(4);

    companion object {
        fun parseInt(value: Int?): DefenseType {
            return when(value){
                0 -> Small
                1 -> Medium
                2 -> Large
                3 -> XL
                4 -> Building
                else -> throw RuntimeException("Unable to parse defense type value $value")
            }
        }
    }
}