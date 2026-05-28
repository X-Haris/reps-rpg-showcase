// Excerpt from RepsRPG — exercises as RPG elements.
// Pushups = Fire, Squats = Air, Sit-ups = Water; bosses take bonus damage from
// their weakness, so which exercise you pick is a tactical choice.
// (The R.drawable references are just icon resources.)

package com.abduloski.repsrpg.data.model

import com.abduloski.repsrpg.R

/**
 * Elements for the RPG system.
 * Each exercise type corresponds to an element.
 * Bosses have weaknesses to specific elements.
 */
enum class Element(val displayName: String, val emoji: String, val iconRes: Int) {
    FIRE("Fire", "🔥", R.drawable.fire),      // Pushups
    AIR("Air", "💨", R.drawable.air),          // Squats
    WATER("Water", "💧", R.drawable.water);    // Situps

    companion object {
        fun fromExerciseType(exerciseType: ExerciseType): Element {
            return when (exerciseType) {
                ExerciseType.PUSHUPS -> FIRE
                ExerciseType.SQUATS -> AIR
                ExerciseType.SITUPS -> WATER
            }
        }
    }
}
