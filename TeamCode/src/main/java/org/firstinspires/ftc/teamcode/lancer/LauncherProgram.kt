package org.firstinspires.ftc.teamcode.lancer

import framework.ftc.cobaltforge.kobaltforge.annotation.State

/**
 * States:
 *
 * min, rising, dropping, unknown
 *
 * input vector:
 * sensors:
 * maxEndstop: Boolean
 * minEndstop: Boolean
 *
 * output vector:
 *
 * launcher0, launcher1, lift
 * Created by Dummyc0m on 2/4/17.
 */
class LauncherProgram {
    @State
    var liftPower = 0.4

    @State
    var launcherPower = 1.0

    val available: Boolean
        get() = state === LauncherState.MIN

    private var prevState = LauncherState.UNKNOWN
    private var state = LauncherState.UNKNOWN

    fun launch(): Boolean {
        if (available) {
            state = LauncherState.RISING
            return true
        }
        return false
    }

    /**
     * should call every loop
     */
    fun update(minStop: Boolean, maxStop: Boolean): DoubleArray {
        if (maxStop && minStop) {
            return DoubleArray(3, { 0.0 })
        }
        if (minStop) {
            if (state === LauncherState.DROPPING || state === LauncherState.UNKNOWN) {
                changeState(LauncherState.MIN)
            }
        }
        if (maxStop) {
            if (state === LauncherState.RISING) {
                changeState(LauncherState.DROPPING)
            }
        }
        if (state === LauncherState.DROPPING) {
            return doubleArrayOf(0.0, 0.0, -liftPower)
        }
        if (state === LauncherState.RISING) {
            return doubleArrayOf(launcherPower, launcherPower, liftPower)
        }
        return DoubleArray(3, { 0.0 })
    }

    fun home() {
        if (state === LauncherState.UNKNOWN) {
            changeState(LauncherState.DROPPING)
        }
    }

    private fun changeState(newState: LauncherState) {
        prevState = state
        state = newState
    }

    enum class LauncherState {
        UNKNOWN, RISING, DROPPING, MIN
    }
}