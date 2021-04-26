package hu.vm.bme.skylordsauctions.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_name_id_mapping")
data class CardNameIdMapping(
    @PrimaryKey var cardName: String,
    @ColumnInfo(name = "smjcardid") var smjCardId: String
)