package com.hd.misaleawianegager

import com.hd.misaleawianegager.domain.remote.ProverbResponse
import com.hd.misaleawianegager.utils.CacheManagerImp
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CacheManagerImpTest {

    private lateinit var cacheManager: CacheManagerImp
    private lateinit var hashMap: HashMap<String, ProverbResponse>

    @Before
    fun setup() {
        // Initialize a real HashMap for testing
        hashMap = HashMap()
        cacheManager = CacheManagerImp(hashMap)
    }

    @Test
    fun `set should add proverb and response to hashmap`() {
        // Given
        val proverb = "Test Proverb"
        val proverbResponse = ProverbResponse(proverb, "Test Author")

        // When
        cacheManager.set(proverbResponse, proverb)

        // Then
        assertEquals(proverbResponse, hashMap[proverb])
        assertEquals(1, hashMap.size)
    }

    @Test
    fun `set should replace existing proverb response if key already exists`() {
        // Given
        val proverb = "Test Proverb"
        val initialResponse = ProverbResponse(proverb, "Initial Author")
        val updatedResponse = ProverbResponse(proverb, "Updated Author")

        // When
        cacheManager.set(initialResponse, proverb)
        cacheManager.set(updatedResponse, proverb)

        // Then
        assertEquals(updatedResponse, hashMap[proverb])
        assertEquals(1, hashMap.size)
    }

    @Test
    fun `get should return null when proverb not in cache`() {
        // Given
        val proverb = "Non-existent Proverb"

        // When
        val result = cacheManager.get(proverb)

        // Then
        assertNull(result)
    }

    @Test
    fun `get should return cached response when proverb exists`() {
        // Given
        val proverb = "Test Proverb"
        val proverbResponse = ProverbResponse(proverb, "Test Author")
        hashMap[proverb] = proverbResponse

        // When
        val result = cacheManager.get(proverb)

        // Then
        assertEquals(proverbResponse, result)
    }
}