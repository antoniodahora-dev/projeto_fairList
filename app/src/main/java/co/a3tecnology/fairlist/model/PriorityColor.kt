package co.a3tecnology.fairlist.model

import android.graphics.Color

enum class PriorityColor {
    LOW,
    MEDIUM,
    HIGH;

    fun getColor() = when(this) {
        LOW -> Color.GREEN
        MEDIUM -> Color.BLUE
        HIGH -> Color.RED
    }
}