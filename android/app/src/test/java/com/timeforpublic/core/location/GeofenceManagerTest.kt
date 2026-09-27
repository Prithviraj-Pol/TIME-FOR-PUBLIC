
package com.timeforpublic.core.location

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GeofenceManagerTest {

    // Pune SDM Office coordinates
    private val officeLat = 18.5314
    private val officeLng = 73.8446

    @Test
    fun `officer within 50 meters is inside geofence`() {
        // Very close location (approx 30m away)
        val currentLat = 18.5316
        val currentLng = 73.8447

        val isInside = GeofenceManager.isWithinOfficeGeofence(
            currentLat = currentLat,
            currentLng = currentLng,
            officeLat = officeLat,
            officeLng = officeLng,
            radiusMeters = 200f
        )
        assertTrue("Officer within 50m should be verified inside 200m geofence", isInside)
    }

    @Test
    fun `officer 500 meters away is outside geofence`() {
        // Location approximately 500m away
        val currentLat = 18.5360
        val currentLng = 73.8446

        val isInside = GeofenceManager.isWithinOfficeGeofence(
            currentLat = currentLat,
            currentLng = currentLng,
            officeLat = officeLat,
            officeLng = officeLng,
            radiusMeters = 200f
        )
        assertFalse("Officer 500m away should NOT be inside 200m geofence", isInside)
    }
}
