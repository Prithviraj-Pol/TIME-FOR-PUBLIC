package com.timeforpublic.core.common

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ResultTest {

    @Test
    fun `test Result Success state`() {
        val result = Result.Success("Test Data")
        assertTrue(result.isSuccess)
        assertFalse(result.isError)
        assertFalse(result.isLoading)
        assertEquals("Test Data", result.getOrNull())
        assertNull(result.errorOrNull())
    }

    @Test
    fun `test Result Error state`() {
        val error = AppError.NotFound("Resource missing")
        val result: Result<String> = Result.Error(error)
        assertFalse(result.isSuccess)
        assertTrue(result.isError)
        assertFalse(result.isLoading)
        assertNull(result.getOrNull())
        assertEquals("Resource missing", result.errorOrNull()?.message)
    }

    @Test
    fun `test Result Loading state`() {
        val result: Result<String> = Result.Loading
        assertFalse(result.isSuccess)
        assertFalse(result.isError)
        assertTrue(result.isLoading)
    }

    @Test
    fun `test Result map transformation`() {
        val result = Result.Success(10)
        val mapped = result.map { it * 2 }
        assertEquals(20, mapped.getOrNull())
    }
}
