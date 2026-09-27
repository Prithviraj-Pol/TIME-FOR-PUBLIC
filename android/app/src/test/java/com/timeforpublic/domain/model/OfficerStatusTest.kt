package com.timeforpublic.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class OfficerStatusTest {

    @Test
    fun `verify exactly 7 mandated availability states exist`() {
        val states = AvailabilityStatus.entries
        assertEquals(7, states.size)
        assertTrue(states.contains(AvailabilityStatus.IN_OFFICE))
        assertTrue(states.contains(AvailabilityStatus.OUT_OF_OFFICE))
        assertTrue(states.contains(AvailabilityStatus.IN_MEETING))
        assertTrue(states.contains(AvailabilityStatus.FIELD_VISIT))
        assertTrue(states.contains(AvailabilityStatus.TRAINING))
        assertTrue(states.contains(AvailabilityStatus.ON_LEAVE))
        assertTrue(states.contains(AvailabilityStatus.UNKNOWN))
    }

    @Test
    fun `officer model adheres to transparency and geofence verification`() {
        val officer = OfficerStatus(
            officerId = "OFF-101",
            name = "Dr. Rajesh Sharma",
            designation = "Tahsildar",
            department = "Revenue",
            officeId = "OFF-01",
            officeName = "Tahsil Office",
            roomNumber = "Room 12",
            status = AvailabilityStatus.IN_OFFICE,
            statusNote = "Available for public hearings",
            lastUpdated = 1700000000000L,
            isGeofenceVerified = true
        )

        assertEquals("OFF-101", officer.officerId)
        assertEquals(AvailabilityStatus.IN_OFFICE, officer.status)
        assertTrue(officer.isGeofenceVerified)
    }
}
