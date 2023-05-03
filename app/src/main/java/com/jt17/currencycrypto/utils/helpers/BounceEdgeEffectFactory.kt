package com.jt17.currencycrypto.utils.helpers

import android.graphics.Canvas
import android.widget.EdgeEffect
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.RecyclerView

class BounceEdgeEffectFactory :
    RecyclerView.EdgeEffectFactory() { //-> EdgeEffectFactory bu recylerviewni chekkalariga effect qo'shish uchun

    companion object {
        /** Величина расстояния перевода при прокрутке списка. */
        private const val OVERSCROLL_TRANSLATION_MAGNITUDE = 0.2f

        /** Величина расстояния перевода, когда список достигает края при бросании.*/
        private const val FLING_TRANSLATION_MAGNITUDE = 0.5f
    }

    override fun createEdgeEffect(
        recyclerView: RecyclerView,
        direction: Int
    ): EdgeEffect { //-> edgeEffect yaratish uchun kerakli bo'lgan ichki funksiya

        //super.createEdgeEffect(recyclerView, direction) buning o'rniga object olib qilib olish kerak
        return object : EdgeEffect(recyclerView.context) {

            /** SpringAnimation effectdan keyingi over-scroll uchun **/
            var translationAnim: SpringAnimation? = null

            override fun onPull(deltaDistance: Float) {
                super.onPull(deltaDistance)
                handlePull(deltaDistance)
            }

            override fun onPull(deltaDistance: Float, displacement: Float) {
                super.onPull(deltaDistance, displacement)
                handlePull(deltaDistance)
            }

            private fun handlePull(deltaDistance: Float) { //-> Bu har scroll teginishida chaqiriladi

                /** recyclerviewni masofasiga ko'chirish uchun **/
                val sign = if (direction == DIRECTION_BOTTOM) -1 else 1
                val translationYDelta =
                    sign * recyclerView.width * deltaDistance * OVERSCROLL_TRANSLATION_MAGNITUDE
                recyclerView.translationY += translationYDelta

                translationAnim?.cancel()
            }

            override fun onRelease() {
                super.onRelease()
                // The finger is lifted. Start the animation to bring translation back to the resting state.
                if (recyclerView.translationY != 0f) {
                    translationAnim = createAnim()?.also { it.start() }
                }

            }

            override fun onAbsorb(velocity: Int) {
                super.onAbsorb(velocity)

                /** list bounce bo'lish holatiga keldi **/
                val sign = if (direction == DIRECTION_BOTTOM) -1 else 1
                val translationVelocity = sign * velocity * FLING_TRANSLATION_MAGNITUDE
                translationAnim?.cancel()
                translationAnim =
                    createAnim().setStartVelocity(translationVelocity)?.also { it.start() }
            }

            override fun draw(canvas: Canvas?): Boolean {// -> oddiy effectni o'chirish uchun
                return false
            }

            override fun isFinished(): Boolean {// isFinished() funksiyasiz onAbsorb() ishga tushmaydi
                return translationAnim?.isRunning?.not() ?: true
            }

            /** Spring animatsiya bizning obyektimizni xususiyasitini animatsiyalaydi **/
            private fun createAnim() = SpringAnimation(recyclerView, SpringAnimation.TRANSLATION_Y)
                .setSpring(SpringForce().apply {
                    finalPosition = 0f // -> yakuniy poszitsiyaga beriladigan qiymat
                    dampingRatio =
                        SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY // -> Bu purjinani cho'zilish koefitsiyanti default holatda 0.5f turadi
                    stiffness =
                        SpringForce.STIFFNESS_LOW // -> Stifness prujinaning qattiqligi hozir low - pas holatda turipti 200f
                })

        }
    }
}