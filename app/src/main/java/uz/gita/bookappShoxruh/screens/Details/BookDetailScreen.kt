package uz.gita.bookappShoxruh.screens.Details

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.banking.utils.l
import uz.gita.bookappShoxruh.R
import uz.gita.bookappShoxruh.data.BookData
import uz.gita.bookappShoxruh.data.UploadData
import uz.gita.bookappShoxruh.data.room.BookEntity
import uz.gita.bookappShoxruh.screens.Details.vm.DetailIntent
import uz.gita.bookappShoxruh.screens.Details.vm.DetailUIState
import uz.gita.bookappShoxruh.screens.Details.vm.DetialViewModel
import uz.gita.bookappShoxruh.screens.Details.vm.impl.DetailViewModelImpl
import kotlin.math.roundToInt

class BookDetailScreen(val bookData: BookData) : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel: DetialViewModel = getViewModel<DetailViewModelImpl>()
        val uiState = viewModel.collectAsState().value

        LaunchedEffect(key1 = 1) {
            viewModel.getBook(bookData)
        }
        DetailScreenContent(uiState, viewModel::onEventDispatcher)
    }


    @Composable
    fun DetailScreenContent(uiState: DetailUIState, onEventDispatcher: (DetailIntent) -> Unit) {
        var isTrue by remember {
            mutableStateOf(false)
        }
        val context = LocalContext.current
        var offlineData: BookEntity? by remember {
            mutableStateOf(null)
        }
        var onlineBookData: BookData by remember {
            mutableStateOf(bookData)
        }
        var cancel by remember {
            mutableStateOf(false)
        }
        var progerss by remember {
            mutableStateOf("")
        }
        when (uiState) {
            is DetailUIState.GetOffline -> {
                offlineData = uiState.bookEntity
            }


            DetailUIState.Cancel -> {

            }
            DetailUIState.Failture -> {
                Toast.makeText(context, "Nimadir xato bo'ldi", Toast.LENGTH_SHORT).show()
            }
            is DetailUIState.Loading -> {
                Toast.makeText(context, "yuklanvotdi", Toast.LENGTH_SHORT).show()
            }
            DetailUIState.NoConnection -> {
                Toast.makeText(context, "internet yo'q", Toast.LENGTH_SHORT).show()
            }
            DetailUIState.Pause -> {
                Toast.makeText(context, "to'xtatildi", Toast.LENGTH_SHORT).show()
            }
            is DetailUIState.Progress -> {
                progerss = uiState.progress.toString()
            }
            is DetailUIState.OnlineBookdata -> {
                onlineBookData = uiState.bookData
            }
        }



        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                Color.White,
                                Color.White,
                                MaterialTheme.colorScheme.primary
                            )
                        )
                    )
                    .padding(vertical = 10.dp)

            ) {


                AsyncImage(
                    model = bookData.imageLink,
                    placeholder = painterResource(id = R.drawable.ic_launcher_background),
                    error = painterResource(id = R.drawable.ic_launcher_background),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(160.dp)
                        .aspectRatio(0.7f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
            }

            Text(
                text =
                "Progress${progerss}"
            )
            Button(onClick = {
                onEventDispatcher(DetailIntent.Pause)
            }) {
                Text(text = "toxtaish")
            }
            Button(onClick = {
                onEventDispatcher(DetailIntent.Resume)
            }) {
                Text(text = "davom etish")
            }

            Button(onClick = {
                onEventDispatcher(DetailIntent.Dislike)
            }) {
                Text(text = "dislike${offlineData?.isLike == -1}")
            }
            Button(onClick = {
                onEventDispatcher(DetailIntent.Like)
            }) {
                Text(text = "like${onlineBookData.like}${offlineData?.isLike == 1}")
            }
            Button(onClick = {
                onEventDispatcher(DetailIntent.Cancel)
            }) {
                Text(text = "Cancel")
            }
            Button(onClick = {
                onEventDispatcher(DetailIntent.Open)
            }) {
                Text(text = "Open")
            }
            Button(onClick = {
                onEventDispatcher(DetailIntent.Download)
            }) {

                Text(text = "Download")
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = bookData.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary
            )

        }
    }


}