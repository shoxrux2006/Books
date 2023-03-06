package uz.gita.bookappShoxruh.screens.PDFScreen

import android.os.Environment
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import com.pdfview.PDFView
import java.io.File

class PdfViewerScreen constructor(private val filePath: String) : AndroidScreen() {
    @Composable
    override fun Content() {
//        val uiState = viewModel.uiState.collectAsState().value
//        KitobimTheme(darkTheme = uiState.isDark, themesType = uiState.themesType, textStyleTypes = uiState.textSizeType) {
        PdfViewerContent(filePath = filePath)
//        }
    }

    @Composable
    fun PdfViewerContent(filePath: String) {
        Surface(modifier = Modifier.fillMaxSize()) {
            val file = File(filePath)

            AndroidView(
                factory = { context ->
                    View.inflate(context, uz.gita.bookappShoxruh.R.layout.fragment_pdf_view, null)
                },
                update = { view ->
                    val pdfview = view.findViewById<PDFView>(uz.gita.bookappShoxruh.R.id.pdfView)
                    pdfview.fromFile(file).show()
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}