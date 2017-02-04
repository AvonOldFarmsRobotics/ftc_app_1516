package org.firstinspires.ftc.teamcode.lancer

import framework.ftc.cobaltforge.kobaltforge.annotation.State

/**
 * FOR STOPPERS LIKE FLIPPY THINGY
 * Created by Dummyc0m on 2/4/17.
 */
class StopperProgram {
    @State
    var pos1 = 0.0

    @State
    var pos2 = 1.0

    private var state = StopperState.UNKNOWN

    fun pos1() {
        state = StopperState.POS_1
    }

    fun pos2() {
        state = StopperState.POS_2
    }

    fun toggle() {
        if (state === StopperState.POS_1) {
            state = StopperState.POS_2
        } else if (state === StopperState.POS_2) {
            state = StopperState.POS_1
        }
    }

    fun update(currentState: Double): Double {
        if (state === StopperState.POS_1) {
            return pos1
        } else if (state === StopperState.POS_2) {
            return pos2
        }
        return currentState
    }

    enum class StopperState {
        UNKNOWN, POS_1, POS_2
    }
}