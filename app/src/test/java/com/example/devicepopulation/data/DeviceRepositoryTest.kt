package com.example.devicepopulation.data

import com.example.devicepopulation.CoroutineRule
import com.example.devicepopulation.data.models.DeviceDetailsModel
import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.data.repository.DeviceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DeviceRepositoryTest {

    private val detailsModel1 = listOf(
        DeviceDetailsModel(
            name = "phoneDetailsName",
            current_price = "34",
            device_image_url = "https://dummyimage.com/200x200/000/fff.jpg",
            description = "Description with a long text",
            rating = 3
        )
    )
    private val detailsModel2 = listOf(
        DeviceDetailsModel(
            name = "tabletDetailsName",
            current_price = "34",
            device_image_url = "https://dummyimage.com/200x200/000/fff.jpg",
            description = "Description with a long text",
            rating = 3
        )
    )
    private val deviceModel1 = DeviceModel(
        id = 1,
        type = "Type1",
        name = "phone",
        status = "status",
        image = "https://dummyimage.com/100x100/000/fff.jpg",
        is_favourite = true,
        details = detailsModel1,
    )
    private val deviceModel2 = DeviceModel(
        id = 2,
        type = "Type2",
        name = "tablet",
        status = "status",
        image = "https://dummyimage.com/100x100/000/fff.jpg",
        is_favourite = true,
        details = detailsModel2,
    )

    private lateinit var deviceRemoteDataSource: MockDataService
    private lateinit var deviceLocalDataSource: MockDataService

    private lateinit var deviceRepository: DeviceRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineRule = CoroutineRule()

    private fun createMockRepository(
        localData: MutableList<DeviceModel>,
        remoteData: MutableList<DeviceModel>
    ) {
        deviceLocalDataSource = MockDataService(localData)
        deviceRemoteDataSource = MockDataService(remoteData)

        deviceRepository = DeviceRepository(
            deviceLocalDataSource,
            deviceRemoteDataSource
        )
    }

    @Test
    fun `fetch all devices from local data source`() = coroutineRule.runBlockingTest {
        val deviceLocalList = mutableListOf(deviceModel1, deviceModel2)
        val deviceRemoteList = mutableListOf(deviceModel1)
        createMockRepository(deviceLocalList, deviceRemoteList)
        // when
        val devices = deviceRepository.fetchDevices()
        // then
        assertThat(devices, IsEqual(deviceLocalList))
    }

    @Test
    fun `force fetch all devices from remote and return local data source`() = coroutineRule.runBlockingTest {
        val deviceLocalList = mutableListOf(deviceModel1, deviceModel2)
        val deviceRemoteList = mutableListOf(deviceModel1)
        createMockRepository(deviceLocalList, deviceRemoteList)
        // when
        val devices = deviceRepository.forceFetchDevices()
        // then
        assertThat(devices, IsEqual(deviceRemoteList))
    }

    @Test
    fun `fetch all devices from empty local data source`() = coroutineRule.runBlockingTest {
        val deviceLocalList: MutableList<DeviceModel> = mutableListOf()
        val deviceRemoteList = mutableListOf(deviceModel1)

        createMockRepository(deviceLocalList, deviceRemoteList)
        // when
        val devices = deviceRepository.fetchDevices()
        // then
        assertThat(devices, IsEqual(deviceRemoteList))
    }

    @Test
    fun `fetch all devices from empty local and remote data source`() = coroutineRule.runBlockingTest {
        val deviceLocalList: MutableList<DeviceModel> = mutableListOf()
        val deviceRemoteList: MutableList<DeviceModel> = mutableListOf()
        createMockRepository(deviceLocalList, deviceRemoteList)
        // when
        val devices = deviceRepository.fetchDevices()
        // then
        assertThat(devices, IsEqual(mutableListOf()))
    }

    @Test
    fun `fetch devices by name from local data source`() = coroutineRule.runBlockingTest {
        val deviceLocalList = mutableListOf(deviceModel1, deviceModel2)
        val deviceRemoteList = mutableListOf(deviceModel1)
        createMockRepository(deviceLocalList, deviceRemoteList)

        val deviceName = "phone"
        // when
        val devices = deviceRepository.fetchDeviceByName(deviceName)
        // then
        assertThat(devices, IsEqual(deviceRemoteList))
    }

    @Test
    fun `fetch devices by name from local data source zero results`() = coroutineRule.runBlockingTest {
        val deviceLocalList = mutableListOf(deviceModel1, deviceModel2)
        val deviceRemoteList = mutableListOf(deviceModel1)
        createMockRepository(deviceLocalList, deviceRemoteList)

        val deviceName = "sensor"
        // when
        val devices = deviceRepository.fetchDeviceByName(deviceName)
        // then
        assertThat(devices, IsEqual(mutableListOf()))
    }

    @Test
    fun `fetch devices by id from local data source`() = coroutineRule.runBlockingTest {
        val deviceLocalList = mutableListOf(deviceModel1, deviceModel2)
        val deviceRemoteList = mutableListOf(deviceModel1, deviceModel2)
        createMockRepository(deviceLocalList, deviceRemoteList)

        val deviceId = 2L
        // when
        val devices = deviceRepository.fetchDeviceDetails(deviceId)
        // then
        assertThat(devices, IsEqual(deviceModel2))
    }

    @Test
    fun `fetch devices by id from local data source zero resilts`() = coroutineRule.runBlockingTest {
        val deviceLocalList = mutableListOf(deviceModel1, deviceModel2)
        val deviceRemoteList = mutableListOf(deviceModel1, deviceModel2)
        createMockRepository(deviceLocalList, deviceRemoteList)

        val deviceId = 3L
        // when
        val devices = deviceRepository.fetchDeviceDetails(deviceId)
        // then
        assertThat(devices, IsEqual(null))
    }
}
