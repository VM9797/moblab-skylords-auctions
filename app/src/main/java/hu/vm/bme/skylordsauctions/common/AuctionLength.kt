package hu.vm.bme.skylordsauctions.common

import java.lang.RuntimeException

enum class AuctionLength(val stringRepresentation: String) {
    Short("8h"),
    Medium("24h"),
    Long("48h");

    companion object {
        fun parseFromStringRepresentation(value: String): AuctionLength {
            return when(value) {
                "8h" -> Short
                "24h" -> Medium
                "48h" -> Long
                else -> throw RuntimeException("Unable to parse auction length value $value")
            }
        }
    }
}