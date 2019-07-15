package kennanobura.co.ke.revit

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import java.nio.file.Files.size
import android.text.method.TextKeyListener.clear
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

lateinit var graphicOverlay: GraphicOverlay<*>

//    val list: MutableList<String> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        graphicOverlay = this.graphic_overlay






        btn_detect.setOnClickListener {
            camera_view.captureImage { cameraKitView, byteArray ->

                camera_view.onStop()

//                alertDialog.show()

                var bitmap = BitmapFactory.decodeByteArray(
                    byteArray,
                    0,
                    byteArray?.size ?: 0)

                bitmap = Bitmap.createScaledBitmap(
                    bitmap,
                    camera_view?.width ?: 0,
                    camera_view?.height ?: 0,
                    false)

                runTextRecognition(bitmap)
            }
            graphicOverlay.clear()
        }
    }


    private fun runTextRecognition(bitmapImage: Bitmap){

        val image: FirebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmapImage)

        val recognizer : FirebaseVisionTextRecognizer = FirebaseVision.getInstance()
            .onDeviceTextRecognizer


        recognizer.processImage(image)
            .addOnSuccessListener{text ->
                processTextResult(text)
            }
            .addOnFailureListener {
                it.printStackTrace()
            }

            .addOnCompleteListener {
                startFragmentDialog()
            }
    }

    private fun startFragmentDialog() {
       val resultListDialogFragment = ResultListDialogFragment()

        resultListDialogFragment.show(supportFragmentManager, resultListDialogFragment.tag)

    }

    private fun processTextResult(texts: FirebaseVisionText?) {

        val blocks = texts?.textBlocks

        if (blocks?.size == 0) {

            Toast.makeText(this, "No text found", Toast.LENGTH_LONG).show()
            return
        }

        graphicOverlay.clear()

        for (i in blocks!!.indices) {

            val lines = blocks[i].lines


            for (j in lines.indices) {
                val elements = lines[j].elements

                for (k in elements.indices) {
//                    val textGraphic = TextGraphic(graphic_overlay, elements[k])
                    val bounds = elements[k].boundingBox

                    val textGraphic = TextGraphic(graphicOverlay, bounds)

                    val resultModel = ResultModel()
                    resultModel.text = elements[k].text

                    LIST_ITEM.add(resultModel)


                    graphicOverlay?.add(GraphicOverlay.Graphic)

                }
            }
        }

    }


    override fun onResume() {
        super.onResume()
        camera_view.onResume()
    }
    override fun onPause() {
        super.onPause()
        camera_view.onPause()
    }
    override fun onStart() {
        super.onStart()
        camera_view.onStart()
    }
    override fun onStop() {
        super.onStop()
        camera_view.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        camera_view.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object{
        val LIST_ITEM: MutableList<ResultModel> = ArrayList<ResultModel>()
    }
}
