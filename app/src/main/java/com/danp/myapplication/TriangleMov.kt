package com.danp.myapplication

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/*
val rotationAngle = remember { mutableStateOf(0f) }
Column(modifier = Modifier.fillMaxSize()) {
    Button(
        onClick = { rotationAngle.value = (0..360).random().toFloat() },
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text("Girar")
    }
    TriangleMov(rotationAngle)
}
*/
//@Composable
//fun TriangleMov(angle: MutableState<Float>,modifier: Modifier) {
//    Column {
//        DrawRectangle()
//        Canvas(Modifier) {
//
//            Log.i("sizes", "height= ${size.height}")
//            Log.i("sizes", "width= ${size.width}")
//
//            val path = Path().apply {
//                moveTo(x = 200.dp.toPx(), y = 150.dp.toPx())
//                lineTo(x = 100.dp.toPx(), y = 350.dp.toPx())
//                lineTo(x = 200.dp.toPx(), y = 300.dp.toPx())
//                lineTo(x = 300.dp.toPx(), y = 350.dp.toPx())
//                close()
//            }
//            rotate(degrees = angle.value, pivot = Offset(x = 200.dp.toPx(), y = 250.dp.toPx())) {
//                drawPath(path, color = Color.Red)
//            }
//        }
//
//    }
//
//}
//@Composable
//fun DrawRectangle(modifier: Modifier = Modifier) {
//
//    Canvas(modifier = Modifier) {
//        val rect = Rect(
//            offset = Offset(x = 100.dp.toPx(), y = 400.dp.toPx()),
//            size = Size(width = 200.dp.toPx(), height = 100.dp.toPx())
//        )
//        drawRect(
//            color = Color.Blue,
//            topLeft = rect.topLeft,
//            size = rect.size
//        )
//    }
//
//}
//@Preview(showBackground = true)
//@Composable
//fun PreviewRect(){
//    val rotationAngle = remember { mutableFloatStateOf(0f) }
//    TriangleMov(angle = rotationAngle,modifier=Modifier.fillMaxSize())
//
//}
