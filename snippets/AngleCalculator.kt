// Excerpt from RepsRPG — the geometry behind rep counting.
// Computes the angle at joint B formed by points A-B-C (e.g. shoulder-elbow-wrist).
// Pure math, no Android dependencies — the rep detector watches these angles
// cross thresholds to decide when a pushup/squat/sit-up has completed.

package com.abduloski.repsrpg.util

import com.abduloski.repsrpg.data.model.LandmarkPoint
import kotlin.math.atan2
import kotlin.math.sqrt

object AngleCalculator {

    fun calculateAngle(
        pointA: LandmarkPoint,
        pointB: LandmarkPoint,
        pointC: LandmarkPoint
    ): Float {
        val vectorBA = Pair(pointA.x - pointB.x, pointA.y - pointB.y)
        val vectorBC = Pair(pointC.x - pointB.x, pointC.y - pointB.y)

        val angleBA = atan2(vectorBA.second.toDouble(), vectorBA.first.toDouble())
        val angleBC = atan2(vectorBC.second.toDouble(), vectorBC.first.toDouble())

        var angle = Math.toDegrees(angleBA - angleBC).toFloat()

        if (angle < 0) angle += 360f
        if (angle > 180f) angle = 360f - angle

        return angle
    }

    fun calculateAngle3D(
        pointA: LandmarkPoint,
        pointB: LandmarkPoint,
        pointC: LandmarkPoint
    ): Float {
        val vectorBA = Triple(
            pointA.x - pointB.x,
            pointA.y - pointB.y,
            pointA.z - pointB.z
        )

        val vectorBC = Triple(
            pointC.x - pointB.x,
            pointC.y - pointB.y,
            pointC.z - pointB.z
        )

        val dotProduct = vectorBA.first * vectorBC.first +
                vectorBA.second * vectorBC.second +
                vectorBA.third * vectorBC.third

        val magnitudeBA = sqrt(
            vectorBA.first * vectorBA.first +
                    vectorBA.second * vectorBA.second +
                    vectorBA.third * vectorBA.third
        )
        val magnitudeBC = sqrt(
            vectorBC.first * vectorBC.first +
                    vectorBC.second * vectorBC.second +
                    vectorBC.third * vectorBC.third
        )

        if (magnitudeBA == 0f || magnitudeBC == 0f) return 0f

        val cosAngle = (dotProduct / (magnitudeBA * magnitudeBC)).coerceIn(-1f, 1f)

        return Math.toDegrees(kotlin.math.acos(cosAngle).toDouble()).toFloat()
    }

    fun calculateElbowAngle(
        shoulder: LandmarkPoint,
        elbow: LandmarkPoint,
        wrist: LandmarkPoint
    ): Float = calculateAngle(shoulder, elbow, wrist)

    fun calculateKneeAngle(
        hip: LandmarkPoint,
        knee: LandmarkPoint,
        ankle: LandmarkPoint
    ): Float = calculateAngle(hip, knee, ankle)

    fun calculateTorsoAngle(
        shoulder: LandmarkPoint,
        hip: LandmarkPoint
    ): Float {
        val dx = shoulder.x - hip.x
        val dy = shoulder.y - hip.y
        val angleFromVertical = Math.toDegrees(atan2(dx.toDouble(), -dy.toDouble())).toFloat()
        return kotlin.math.abs(angleFromVertical)
    }

    fun calculateVerticalDisplacement(
        currentY: Float,
        baselineY: Float
    ): Float = baselineY - currentY

    fun calculateDistance(
        point1: LandmarkPoint,
        point2: LandmarkPoint
    ): Float {
        val dx = point2.x - point1.x
        val dy = point2.y - point1.y
        return sqrt(dx * dx + dy * dy)
    }
}
