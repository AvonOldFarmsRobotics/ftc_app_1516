package org.firstinspires.ftc.teamcode.cv

import android.app.Activity
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.holonomic.HolonomicOpMode
import org.opencv.android.*
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Collect data for OpenCV
 * Created by Dummyc0m on 10/31/16.
 */
@TeleOp(name = "CVCollect")
class CVCollectionOpMode : HolonomicOpMode() {
    lateinit var mOpenCvCameraView: CameraBridgeViewBase
    lateinit var mGray: Mat
    lateinit var mRgba: Mat
    private var frameIndex = 0
    private var lastSave = System.currentTimeMillis()
    private val folderName = System.currentTimeMillis().toString()

    private val parentFolder = File(Environment.getExternalStorageDirectory(), "frames")

    override fun construct() {
        super.construct()
        val sd = File(parentFolder, folderName)
        val metaFile = File(sd, "metadata")
        val writer = FileWriter(metaFile)

        onInit {

            mOpenCvCameraView = (hardwareMap.appContext as Activity).findViewById(com.qualcomm.ftcrobotcontroller.R.id.cvView) as CameraBridgeViewBase
            mOpenCvCameraView.visibility = CameraBridgeViewBase.VISIBLE
            mOpenCvCameraView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK)
            mOpenCvCameraView.setCvCameraViewListener(object : CameraBridgeViewBase.CvCameraViewListener {
                override fun onCameraViewStarted(width: Int, height: Int) {
                    mGray = Mat(height, width, CvType.CV_8UC1)
                    mRgba = Mat(height, width, CvType.CV_8UC4)
                }

                override fun onCameraViewStopped() {
                    mGray.release();
                    mRgba.release()
                }

                override fun onCameraFrame(inputFrame: Mat): Mat {
                    mGray.release()
                    mRgba.release()
                    Imgproc.cvtColor(inputFrame, mGray, Imgproc.COLOR_RGB2GRAY)
                    val output = mGray.clone()

                    Imgproc.GaussianBlur(mGray, mGray, Size(5.0, 5.0), 1.0)
                    Imgproc.Canny(mGray, mGray, 50.0, 150.0)

                    Imgproc.resize(output, output, Size(50.0, 50.0))
                    if (System.currentTimeMillis() > lastSave + 500L) {
                        lastSave = System.currentTimeMillis()
                        deployAsync {
                            val bmp = Bitmap.createBitmap(output.cols(), output.rows(), Bitmap.Config.ARGB_8888)
                            Utils.matToBitmap(output, bmp)
                            output.release()
                            Log.d("CV", hardwareMap.appContext.filesDir.absolutePath)
                            if (sd.exists() || sd.mkdir()) {
                                val dest = File(sd, "image-${("000000" + frameIndex).substring(frameIndex.toString().length)}.png")

                                val x = this@CVCollectionOpMode.x
                                val y = this@CVCollectionOpMode.y
                                val leftX = this@CVCollectionOpMode.leftX

                                frameIndex++
                                var out: FileOutputStream? = null
                                try {
                                    writer.appendln(StringBuilder().append(x).append(" ").append(y).append(" ").append(leftX))
                                    writer.flush()
                                    out = FileOutputStream(dest)
                                    bmp.compress(Bitmap.CompressFormat.PNG, 100, out) // bmp is your Bitmap instance
                                    // PNG is a lossless format, the compression factor (100) is ignored
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    Log.d("CV", e.message)
                                } finally {
                                    try {
                                        if (out != null) {
                                            out.close()
                                            Log.d("CV", "OK!!")
                                        }
                                        writer.close()
                                    } catch (e: IOException) {
                                        Log.d("CV", e.message + "Error")
                                        e.printStackTrace()
                                    }

                                }
                            }
                        }
                    }
                    return mGray
                }
            })
        }

        onStart {
            val mLoaderCallback = object : BaseLoaderCallback(hardwareMap.appContext) {
                override fun onManagerConnected(status: Int) {
                    when (status) {
                        LoaderCallbackInterface.SUCCESS -> {
                            mOpenCvCameraView.enableView()
                        }
                        else -> {
                            super.onManagerConnected(status)
                        }
                    }
                }
            }

            if (!OpenCVLoader.initDebug()) {
                OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_10, hardwareMap.appContext, mLoaderCallback)
            } else {
                mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
            }
        }

        onStop {
            writer.flush()
            writer.close()
            shutdown()
            mOpenCvCameraView.disableView()
        }
    }

    companion object {
        private val executor = Executors.newCachedThreadPool()

        init {
            System.loadLibrary("opencv_java")
        }

        fun deployAsync(runnable: () -> Unit) {
            executor.submit(runnable)
        }

        fun shutdown() {
            executor.shutdown()
            executor.awaitTermination(10, TimeUnit.SECONDS)
        }
    }
}

