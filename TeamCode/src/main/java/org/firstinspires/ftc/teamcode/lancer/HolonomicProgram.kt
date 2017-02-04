package org.firstinspires.ftc.teamcode.lancer

import framework.ftc.cobaltforge.kobaltforge.annotation.State
import framework.ftc.cobaltforge.kobaltforge.util.MathHelper.dotProduct
import framework.ftc.cobaltforge.kobaltforge.util.MathHelper.magnitude
import framework.ftc.cobaltforge.kobaltforge.util.abs
import framework.ftc.cobaltforge.kobaltforge.util.sqrt

/**
 * Created by Dummyc0m on 2/4/17.
 */
class HolonomicProgram {
    @State
    var vecUnit = 1 / 2.0.sqrt()

    private val motorMagnitude = (vecUnit * vecUnit * 2).sqrt() //Math.sqrt(2);
    private var vec1X = -vecUnit
    private var vec1Y = -vecUnit
    private var vec2X = -vecUnit
    private var vex2Y = vecUnit

    private var dir2Cached = direction(vec2X, vex2Y)
    private var dir1Cached = direction(vec1X, vec1Y)

    fun calculateMotorPower(leftX: Float, leftY: Float, rightX: Float, rightY: Float): DoubleArray {
        val calcX = rightX.toDouble()
        val calcY = if (leftY.abs() > rightY.abs()) leftY.toDouble() else rightY.toDouble()
        val calcRot = leftX.toDouble()

        val signedAngle1 = dir1Cached - direction(calcX, calcY)
        val signedAngle2 = dir2Cached - direction(calcX, calcY)

        val joystickMagnitude = magnitude(calcX, calcY)

        val val1 = clamp(dotProduct(motorMagnitude, joystickMagnitude, signedAngle1))
        val val2 = clamp(dotProduct(motorMagnitude, joystickMagnitude, signedAngle2))

        if (Math.abs(val1) > 0.1 || Math.abs(val2) > 0.1) {
            return doubleArrayOf(val1, val2, val1, val2)
        } else if (Math.abs(calcRot) > 0.1) {
            return doubleArrayOf(calcRot, -calcRot, -calcRot, calcRot)
        } else {
            return DoubleArray(4, { 0.0 })
        }
    }

    /**
     * @param x
     * *
     * @param y
     * *
     * @return theta
     */
    private fun direction(x: Double, y: Double): Double {
        return Math.atan2(y, x)
    }

    private fun clamp(a: Double, max: Double = 1.0, min: Double = -1.0): Double {
        return if (a > max) max else if (a < min) min else a
    }
}