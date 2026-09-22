package com.timeforpublic.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timeforpublic.domain.model.AvailabilityStatus
import com.timeforpublic.ui.theme.StatusAvailableColor
import com.timeforpublic.ui.theme.StatusBusyColor
import com.timeforpublic.ui.theme.StatusFieldDutyColor
import com.timeforpublic.ui.theme.StatusLeaveColor
import com.timeforpublic.ui.theme.StatusOfflineColor

@Composable
fun StatusBadge(
    status: AvailabilityStatus,
    modifier: Modifier = Modifier
) {
    val (statusColor, statusLabel) = when (status) {
        AvailabilityStatus.AVAILABLE -> StatusAvailableColor to "Available"
        AvailabilityStatus.IN_MEETING -> StatusBusyColor to "In Meeting"
        AvailabilityStatus.ON_FIELD_DUTY -> StatusFieldDutyColor to "Field Duty"
        AvailabilityStatus.ON_LEAVE -> StatusLeaveColor to "On Leave"
        AvailabilityStatus.OFFLINE -> StatusOfflineColor to "Offline"
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(statusColor.copy(alpha = 0.15f))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(statusColor)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = statusLabel,
            color = statusColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
