package com.timeforpublic.feature.officer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.GpsNotFixed
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timeforpublic.domain.model.AvailabilityStatus
import com.timeforpublic.ui.components.AppButton
import com.timeforpublic.ui.components.AppOutlinedButton
import com.timeforpublic.ui.components.AppTextField
import com.timeforpublic.ui.components.StatusBadge
import com.timeforpublic.ui.theme.IndianEmerald
import com.timeforpublic.ui.theme.NavyLight
import com.timeforpublic.ui.theme.NavyPrimary
import com.timeforpublic.ui.theme.SaffronAccent
import com.timeforpublic.ui.theme.SlateBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfficerDashboardScreen(
    viewModel: OfficerViewModel,
    onNavigateBack: () -> Unit
) {
    val officerStatus by viewModel.officerStatus.collectAsState()
    val isGeofenceInside by viewModel.isGeofenceInside.collectAsState()
    var selectedStatus by remember { mutableStateOf(officerStatus?.status ?: AvailabilityStatus.IN_OFFICE) }
    var statusNote by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Officer Portal", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = NavyPrimary)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Profile Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(NavyPrimary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = NavyPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = officerStatus?.name ?: "Dr. Rajesh Sharma",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = officerStatus?.designation ?: "Tahsildar & Sub-Divisional Magistrate",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        Text(
                            text = officerStatus?.officeName ?: "Sub-Divisional Magistrate Office",
                            style = MaterialTheme.typography.labelSmall,
                            color = NavyLight
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Geofence Status Card (200m perimeter)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isGeofenceInside) IndianEmerald.copy(alpha = 0.08f) else Color(0xFFFEE2E2)
                )
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isGeofenceInside) Icons.Default.GpsFixed else Icons.Default.GpsNotFixed,
                        contentDescription = null,
                        tint = if (isGeofenceInside) IndianEmerald else MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isGeofenceInside) "Geofence Verified (Inside 200m Perimeter)" else "Geofence Unverified (Outside Office Perimeter)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (isGeofenceInside) IndianEmerald else MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "Play Services event-driven monitoring active. Zero continuous GPS tracking.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Current Active Availability Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Current Live State",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                officerStatus?.let {
                    StatusBadge(status = it.status)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Update Availability Section (Mandated 7 states)
            Text(
                text = "Update Availability Status",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            val mandatedStates = listOf(
                AvailabilityStatus.IN_OFFICE,
                AvailabilityStatus.OUT_OF_OFFICE,
                AvailabilityStatus.IN_MEETING,
                AvailabilityStatus.FIELD_VISIT,
                AvailabilityStatus.TRAINING,
                AvailabilityStatus.ON_LEAVE
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                mandatedStates.chunked(2).forEach { rowStates ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowStates.forEach { state ->
                            val isSelected = selectedStatus == state
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        if (isSelected) NavyPrimary else SlateBorder.copy(alpha = 0.4f)
                                    )
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                androidx.compose.foundation.text.ClickableText(
                                    text = androidx.compose.ui.text.AnnotatedString(
                                        when (state) {
                                            AvailabilityStatus.IN_OFFICE -> "In Office"
                                            AvailabilityStatus.OUT_OF_OFFICE -> "Out of Office"
                                            AvailabilityStatus.IN_MEETING -> "In Meeting"
                                            AvailabilityStatus.FIELD_VISIT -> "Field Visit"
                                            AvailabilityStatus.TRAINING -> "Training"
                                            AvailabilityStatus.ON_LEAVE -> "On Leave"
                                            AvailabilityStatus.UNKNOWN -> "Unknown"
                                        }
                                    ),
                                    style = androidx.compose.ui.text.TextStyle(
                                        color = if (isSelected) Color.White else NavyPrimary,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 13.sp
                                    ),
                                    onClick = { selectedStatus = state }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AppTextField(
                value = statusNote,
                onValueChange = { statusNote = it },
                label = "Public Transparency Note",
                placeholder = "e.g., Reviewing citizenship applications"
            )

            Spacer(modifier = Modifier.height(20.dp))

            AppButton(
                text = "Broadcast Live Status",
                onClick = {
                    viewModel.updateStatus(selectedStatus, statusNote)
                },
                containerColor = SaffronAccent
            )

            Spacer(modifier = Modifier.height(12.dp))

            AppOutlinedButton(
                text = if (isGeofenceInside) "Simulate Geofence Exit" else "Simulate Geofence Entry",
                onClick = { viewModel.toggleGeofenceSimulation() }
            )
        }
    }
}
