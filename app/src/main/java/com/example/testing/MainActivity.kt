package com.example.testing

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore.Files
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import com.example.testing.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var player: MediaPlayer
    private lateinit var runnable: Runnable
    private val handler = Handler(Looper.getMainLooper())
    private var dragging = false
    //For commit

    //For commit in branch 2

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        player = MediaPlayer.create(this, R.raw.sample)
        //"https://motivy.sixhands.co/media/courses/2022/06/17/audios/Biocuo_-_Concentration_Original_Mix_F6RwaqU.mp3"
//        val file = File("C:/Users/petrs/Downloads/sample.wav")
        binding.apply {
//            audioWaveView.scaledData = assets.open("sample.wav").readBytes()
            waveFormView.setSampleFrom(R.raw.sample)
            waveFormView.onProgressChanged = object : com.masoudss.lib.SeekBarOnProgressChanged {
                override fun onProgressChanged(
                    waveformSeekBar: com.masoudss.lib.WaveformSeekBar,
                    progress: Float,
                    fromUser: Boolean
                ) {
                    if (progress >= getEndProgress() - 0.1) {
                        player.pause()
                        player.seekTo((player.duration * getStartProgress() / 100).toInt())
                    }
                }
            }
            runnable = Runnable {
//                waveFormView.progress =
//                    player.currentPosition.toFloat() / player.duration.toFloat() * 100
                handler.postDelayed(runnable, 100)
            }
            handler.postDelayed(runnable, 100)
//            btn.setOnClickListener {
//                if (!player.isPlaying) {
//                    player.start()
//                } else {
//                    player.pause()
//                }
//            }

            leftTrimmer.setOnTouchListener { _, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                    if (motionEvent.rawX <= rightTrimmer.x + rightTrimmer.width + 6 && motionEvent.rawX > waveFormView.x + 22)
                        leftTrimmer.x = motionEvent.rawX - leftTrimmer.width * 1.75f

                    val topParams = topBorder.layoutParams as ViewGroup.LayoutParams
                    topParams.width = (rightTrimmer.x - leftTrimmer.x).toInt()
                    topBorder.layoutParams = topParams
                    topBorder.x = leftTrimmer.x + leftTrimmer.width / 2

                    val bottomParams = bottomBorder.layoutParams as ViewGroup.LayoutParams
                    bottomParams.width = (rightTrimmer.x - leftTrimmer.x).toInt()
                    bottomBorder.layoutParams = bottomParams
                    bottomBorder.x = leftTrimmer.x + leftTrimmer.width / 2

//                    onProgressListener?.onProgressChanged(getStartProgress(), getEndProgress())
//                    onProgressChanged(getStartProgress(), getEndProgress())
                }
                true
            }

            rightTrimmer.setOnTouchListener {_, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                    if (motionEvent.rawX >= leftTrimmer.x + leftTrimmer.width * 3 - 22 && motionEvent.rawX < waveFormView.width + waveFormView.x + 4)
                        rightTrimmer.x = motionEvent.rawX - rightTrimmer.width * 1.75f

                    val topParams = topBorder.layoutParams as ViewGroup.LayoutParams
                    topParams.width = (rightTrimmer.x - leftTrimmer.x).toInt()
                    topBorder.layoutParams = topParams
                    topBorder.x = leftTrimmer.x + leftTrimmer.width / 2

                    val bottomParams = bottomBorder.layoutParams as ViewGroup.LayoutParams
                    bottomParams.width = (rightTrimmer.x - leftTrimmer.x).toInt()
                    bottomBorder.layoutParams = bottomParams
                    bottomBorder.x = leftTrimmer.x + leftTrimmer.width / 2

//                    onProgressListener?.onProgressChanged(getStartProgress(), getEndProgress())
//                    onProgressChanged(getStartProgress(), getEndProgress())
                }
                true
            }

            waveFormView.setOnTouchListener { _, motionEvent ->
                return@setOnTouchListener true
            }
        }
    }

    fun getStartProgress(): Float {
            return binding.leftTrimmer.x / binding.customTrimmer.width * 100
    }

    fun getEndProgress(): Float {
        return (binding.rightTrimmer.x + binding.rightTrimmer.width) / binding.customTrimmer.width * 100
    }
}