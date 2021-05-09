package hu.vm.bme.skylordsauctions.data

import hu.vm.bme.skylordsauctions.network.cardbase.model.Card
import hu.vm.bme.skylordsauctions.network.cardbase.model.Image

object MockCards {
    val abomination = Card(name = "Abomination", image = Image(name = "Abomination [B]"))
    val amiiMonument = Card(name = "Amii Monument", image = Image(name = "Amii Monument [B]"))
    val shaman = Card(name = "Shaman", image = Image(name = "Shaman"))
    val protectorsSeal = Card(name = "Protector's Seal (Lost Souls)", image = Image(name = "Protector's Seal (Lost Souls)"))
    val banditos = Card(name = "Banditos", image = Image(name = "Banditos [N]"))
    val cardWithNoImage = Card(name = "Sample", image = null)
}