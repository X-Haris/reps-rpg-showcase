// Excerpt from RepsRPG — the pose domain model.
// LandmarkPoint + a 17-joint PoseData skeleton (the shape ML Kit returns) with
// confidence gates (hasValidUpperBody / hasValidFullBody) and midpoint helpers
// the rep-detection logic relies on before it ever counts a rep.

package com.abduloski.repsrpg.data.model

import android.graphics.PointF

data class LandmarkPoint(
    val x: Float,
    val y: Float,
    val z: Float = 0f,
    val confidence: Float = 0f
) {
    fun toPointF(): PointF = PointF(x, y)

    companion object {
        val EMPTY = LandmarkPoint(0f, 0f, 0f, 0f)
    }
}

data class PoseData(
    val nose: LandmarkPoint = LandmarkPoint.EMPTY,
    val leftEye: LandmarkPoint = LandmarkPoint.EMPTY,
    val rightEye: LandmarkPoint = LandmarkPoint.EMPTY,
    val leftEar: LandmarkPoint = LandmarkPoint.EMPTY,
    val rightEar: LandmarkPoint = LandmarkPoint.EMPTY,
    val leftShoulder: LandmarkPoint = LandmarkPoint.EMPTY,
    val rightShoulder: LandmarkPoint = LandmarkPoint.EMPTY,
    val leftElbow: LandmarkPoint = LandmarkPoint.EMPTY,
    val rightElbow: LandmarkPoint = LandmarkPoint.EMPTY,
    val leftWrist: LandmarkPoint = LandmarkPoint.EMPTY,
    val rightWrist: LandmarkPoint = LandmarkPoint.EMPTY,
    val leftHip: LandmarkPoint = LandmarkPoint.EMPTY,
    val rightHip: LandmarkPoint = LandmarkPoint.EMPTY,
    val leftKnee: LandmarkPoint = LandmarkPoint.EMPTY,
    val rightKnee: LandmarkPoint = LandmarkPoint.EMPTY,
    val leftAnkle: LandmarkPoint = LandmarkPoint.EMPTY,
    val rightAnkle: LandmarkPoint = LandmarkPoint.EMPTY,
    val overallConfidence: Float = 0f,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun hasValidUpperBody(minConfidence: Float = 0.5f): Boolean {
        return leftShoulder.confidence >= minConfidence &&
                rightShoulder.confidence >= minConfidence &&
                leftElbow.confidence >= minConfidence &&
                rightElbow.confidence >= minConfidence &&
                leftWrist.confidence >= minConfidence &&
                rightWrist.confidence >= minConfidence
    }

    fun hasValidLowerBody(minConfidence: Float = 0.5f): Boolean {
        return leftHip.confidence >= minConfidence &&
                rightHip.confidence >= minConfidence &&
                leftKnee.confidence >= minConfidence &&
                rightKnee.confidence >= minConfidence &&
                leftAnkle.confidence >= minConfidence &&
                rightAnkle.confidence >= minConfidence
    }

    fun hasValidFullBody(minConfidence: Float = 0.5f): Boolean {
        return hasValidUpperBody(minConfidence) && hasValidLowerBody(minConfidence)
    }

    fun getAverageShoulderY(): Float {
        return (leftShoulder.y + rightShoulder.y) / 2f
    }

    fun getMidpoint(point1: LandmarkPoint, point2: LandmarkPoint): LandmarkPoint {
        return LandmarkPoint(
            x = (point1.x + point2.x) / 2f,
            y = (point1.y + point2.y) / 2f,
            z = (point1.z + point2.z) / 2f,
            confidence = minOf(point1.confidence, point2.confidence)
        )
    }

    fun getHipCenter(): LandmarkPoint = getMidpoint(leftHip, rightHip)

    fun getShoulderCenter(): LandmarkPoint = getMidpoint(leftShoulder, rightShoulder)
}
