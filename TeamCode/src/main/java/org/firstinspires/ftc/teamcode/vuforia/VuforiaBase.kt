package org.firstinspires.ftc.teamcode.vuforia

import framework.ftc.cobaltforge.kobaltforge.KobaltForge
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference
import org.firstinspires.ftc.robotcore.external.navigation.Orientation

/**
 * Created by Dummyc0m on 10/28/16.
 */
class VuforiaBase : KobaltForge() {
    protected var enableCamera = true

    override fun construct() {
        onInit {

        }
    }

    fun constructMatrix(x: Float = 0f, y: Float = 0f, z: Float = 0f,
                        rotationX: Float = 0f, rotationY: Float = 0f, rotationZ: Float = 0f): OpenGLMatrix {
        return OpenGLMatrix.translation(x, y, z)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ,
                        AngleUnit.DEGREES, rotationX, rotationY, rotationZ))
    }
}