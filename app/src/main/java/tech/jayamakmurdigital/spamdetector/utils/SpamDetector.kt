package tech.jayamakmurdigital.spamdetector.utils

import android.content.Context
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import tech.jayamakmurdigital.spamdetector.ml.Mobile
import kotlin.math.roundToInt

class SpamDetector(val context: Context) {
    private val vocab = context.assets.open("vocab.tsv").reader().readLines().toTypedArray()

    private fun encodeText(text: String): Array<Float> {
        val strings = Regex("[^A-Za-z0-9 ]").replace(text, "").toLowerCase().split(" ")
        return ArrayList<Float>().apply {
            for (i in 0..31) if (strings.size > i) add(vocab.indexOf(strings[i]).toFloat() + 1) else add(0F)
        }.toTypedArray()
    }

    fun predict(text: String): Int {
        val model = Mobile.newInstance(context)
        val inputLayer = TensorBuffer.createFixedSize(intArrayOf(1, 32), DataType.FLOAT32)
        inputLayer.loadArray(encodeText(text).toFloatArray())
        val outputs = model.process(inputLayer)
        val result = outputs.outputFeature0AsTensorBuffer
        model.close()
        return (result.getFloatValue(0) * 100).roundToInt()
    }
}