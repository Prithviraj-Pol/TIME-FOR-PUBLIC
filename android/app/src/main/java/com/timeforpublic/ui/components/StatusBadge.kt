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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timeforpublic.domain.model.AvailabilityStatus
import com.timeforpublic.ui.theme.StatusFieldVisitColor
import com.timeforpublic.ui.theme.StatusInMeetingColor
import com.timeforpublic.ui.theme.StatusInOfficeColor
import com.timeforpublic.ui.theme.StatusOnLeaveColor
import com.timeforpublic.ui.theme.StatusOutOfOfficeColor
import com.timeforpublic.ui.theme.StatusTrainingColor
import com.timeforpublic.ui.theme.StatusUnknownColor

/**
 * Renders one of the 7 mandated officer availability states
 * with consistent color-coding and high-contrast typography.
 */
@Composable
fun StatusBadge(
    status: AvailabilityStatus,
    modifier: Modifier = Modifier
) {
    val (statusColor, statusLabel) = when (status) {
        AvailabilityStatus.IN_OFFICE -> StatusInOfficeColor to "In Office"
        AvailabilityStatus.OUT_OF_OFFICE -> StatusOutOfOfficeColor to "Out of Office"
        AvailabilityStatus.IN_MEETING -> StatusInMeetingColor to "In Meeting"
        AvailabilityStatus.FIELD_VISIT -> StatusFieldVisitColor to "Field Visit"
        AvailabilityStatus.TRAINING -> StatusTrainingColor to "In Training"
        AvailabilityStatus.ON_LEAVE -> StatusOnLeaveColor to "On Leave"
        AvailabilityStatus.UNKNOWN -> StatusUnknownColor to "Unknown"
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
