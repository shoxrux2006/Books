package uz.gita.bookappShoxruh.screens.Main.extensions

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import uz.gita.bookappShoxruh.R
import uz.gita.bookappShoxruh.data.BookData
import uz.gita.bookappShoxruh.ui.theme.BooksTheme
import uz.gita.bookappShoxruh.utils.trim

@Composable
fun BookItem(bookData: BookData, click: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { click() }
            .background(MaterialTheme.colorScheme.surface)
            .padding(10.dp)

    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = bookData.bookName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = bookData.imageLink,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background),
                modifier = Modifier
                    .width(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .aspectRatio(0.7f),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(start = 10.dp)
            ) {
                bodyText(title = "Yozuvchi :", text = bookData.author)
                bodyText(title = "Janr :", text = bookData.genre)
                bodyText(title = "Yili :", text = bookData.realizedDate.toString())
                Spacer(modifier = Modifier.weight(1f))
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    bodyIcon(
                        id = R.drawable.download,
                        text = bookData.downloads.trim()
                    )
                    bodyIcon(
                        id = R.drawable.baseline_thumb_up_alt_24,
                        text = bookData.like.trim()
                    )
                }
            }
        }
    }
}

@Composable
private fun bodyIcon(@DrawableRes id: Int, text: String) {
    Spacer(modifier = Modifier.size(10.dp))
    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = id),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "icon"
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelSmall
        )
    }
    Spacer(modifier = Modifier.size(10.dp))
}


@Composable
private fun bodyText(title: String, text: String) {
    Spacer(modifier = Modifier.size(5.dp))
    Row() {
        Text(
            maxLines = 1,
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            maxLines = 1,
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary,
        )
    }
    Spacer(modifier = Modifier.size(5.dp))
}


@Preview
@Composable
fun prevs() {
    BooksTheme() {
//        BookItem()
    }
}