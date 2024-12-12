package id.hoptima.util

import android.animation.ObjectAnimator
import android.view.View

object AnimationUtil {
    fun fadeIn(target: View): ObjectAnimator {
        return ObjectAnimator.ofFloat(target, View.ALPHA, 0f, 1f)
    }
}