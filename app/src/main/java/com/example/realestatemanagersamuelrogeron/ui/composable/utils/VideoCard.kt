package com.example.realestatemanagersamuelrogeron.ui.composable.utils

import android.net.Uri
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.realestatemanagersamuelrogeron.utils.RemIcon

@Composable
fun VideoCard(videoUri: Uri,
              modifier: Modifier = Modifier,
              isSuppressButtonEnable: Boolean = false,
              onSuppressClick: (Uri) -> Unit = {}) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUri))
            prepare()
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        if(isSuppressButtonEnable){
            if (isSuppressButtonEnable) {
                Icon(modifier = Modifier.clickable {
                    onSuppressClick(videoUri)
                }, imageVector = RemIcon.Remove, contentDescription = null)
            }
        }
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    useController = true
                }
            }
        )
    }
}
