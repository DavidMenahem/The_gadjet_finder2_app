package com.david.thegadjetfinder.model

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View


class CircleView (context: Context, x :Int, y : Int): View(context){
    private lateinit var paint: Paint
    private var x = x
    private var y = y


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(getX(), getY())
        paint = Paint(Paint.ANTI_ALIAS_FLAG);
        paint.color = Color.BLUE
        canvas.drawCircle(width / 2F, height / 2F, 50F, paint)
    }

    override fun getX(): Float {
        return x.toFloat()
    }

    override fun getY(): Float {
        return y.toFloat()
    }
}