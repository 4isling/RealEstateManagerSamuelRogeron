package com.example.realestatemanagersamuelrogeron.ui.add_screen.composable

import android.Manifest
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.realestatemanagersamuelrogeron.utils.PermissionUtils
import com.example.realestatemanagersamuelrogeron.utils.RemIcon
import java.io.File

@Suppress()
@Composable
fun CameraPreviewTablet(
    onImageCaptured: (Uri?) -> Unit,
    onClickBack: () -> Unit = {},
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val imageCapture = remember { ImageCapture.Builder().build() }

    AndroidView(
        factory = { ctx ->
            PreviewView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        update = { previewView ->
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture // Bind the image capture use case here
                )
            } catch (exc: Exception) {
                Log.e("CameraPreview", "Use case binding failed", exc)
            }
        }
    )

    // Button for capturing the image
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        FloatingActionButton(onClick =  onClickBack) {
            Icon(imageVector = RemIcon.ArrowBack, contentDescription = "Back")
        }
        Button(
            modifier = Modifier
                .padding(8.dp)
                .align(alignment = Alignment.BottomCenter),
            onClick = {
                val photoFile = File(
                    context.externalMediaDirs.first(),
                    "${System.currentTimeMillis()}.jpg"
                )

                val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

                imageCapture.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                            onImageCaptured(Uri.fromFile(photoFile))
                        }

                        override fun onError(exc: ImageCaptureException) {
                            Log.e("CameraPreview", "Photo capture failed: ${exc.message}", exc)
                            onImageCaptured(null)
                        }
                    }
                )
            }
        ) {
            Text("Take Picture")
        }
    }
}
@Composable
fun CameraHandlerTablet(
    onImageCaptured: (Uri?) -> Unit,
    onCloseCamera: () -> Unit,
) {
    val context = LocalContext.current
    val openCamera = remember {
        mutableStateOf(false)
    }
    val hasCameraPermission = PermissionUtils.requestPermission(
        Manifest.permission.CAMERA,
        onPermissionResult = { isGranted ->
            if (isGranted) {
                // Permission granted, open camera
                openCamera.value = true // Call your CameraPreview Composable here
            } else {
                // Permission denied, show a toast
                Toast.makeText(
                    context,
                    "Camera permission is required to use the camera.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    )

    // If permission is already granted, directly show camera preview
    if (hasCameraPermission) {
        CameraPreviewTablet(
            onImageCaptured = { uri ->
                if (uri != null) {
                    onImageCaptured(uri)
                }
            },
            onClickBack = onCloseCamera // Use the new callback here
        )
    }
}