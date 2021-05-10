package hu.vm.bme.skylordsauctions

import hu.vm.bme.skylordsauctions.data.CardNameIdMapping
import hu.vm.bme.skylordsauctions.data.CardNameIdMappingDAO

class DaoMock: CardNameIdMappingDAO {
    private val mappings = mutableListOf<CardNameIdMapping>()

    override suspend fun getMappingByCardName(cardName: String): CardNameIdMapping? {
        return mappings.find { it.cardName == cardName }
    }

    override suspend fun insertMappings(vararg mappings: CardNameIdMapping) {
        this.mappings.addAll(mappings)
    }

    override suspend fun getAllMappings(): List<CardNameIdMapping> {
        return mappings
    }

    override suspend fun removeAll() {
        mappings.clear()
    }
}