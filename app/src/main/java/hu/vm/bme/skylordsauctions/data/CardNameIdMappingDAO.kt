package hu.vm.bme.skylordsauctions.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CardNameIdMappingDAO {

    @Query("SELECT * FROM card_name_id_mapping WHERE cardName = :cardName")
    suspend fun getMappingByCardName(cardName: String): CardNameIdMapping?

    @Query("SELECT * FROM card_name_id_mapping")
    suspend fun getAllMappings(): List<CardNameIdMapping>

    @Insert
    suspend fun insertMappings(vararg mappings: CardNameIdMapping)

    @Query("DELETE FROM card_name_id_mapping")
    suspend fun removeAll()
}