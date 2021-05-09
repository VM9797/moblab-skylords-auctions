package hu.vm.bme.skylordsauctions.data

interface AppDaoProvider {
    fun cardNameIdMappingDao(): CardNameIdMappingDAO
}