package com.example.level3_task_22.ui.theme.screens

import android.os.Build
import android.util.Patterns
import android.webkit.URLUtil
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.level3_task_22.PortalViewModel
import com.example.level3_task_22.R


@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPortalScreen(navController: NavHostController, viewModel: PortalViewModel) {
    val context = LocalContext.current
    val localDensity = LocalDensity.current
    var HeightDp by remember { mutableStateOf(0.dp) }
    var texto by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    HeightDp = with(localDensity) { coordinates.size.height.toDp() }},
                title = {
                    Text(
                        text = context.getString(R.string.app_name),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0,30,49))
            )
        },
        content = {innerPadding -> AddPortal(Modifier.padding(innerPadding),
                                             navController,
                                             viewModel,
                                             texto,
                                             HeightDp)},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPortal(padding: Modifier,
              navController: NavHostController,
              viewModel: PortalViewModel,
              text: String,
              hei:Dp) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = hei + 5.dp)
    ){
        Row (
            modifier = Modifier.padding(10.dp)
        ){
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                // below line is used to add placeholder ("hint") for our text field.
                placeholder = { Text(text = "") },
                onValueChange = {
                    name = it
                },
                label = { Text(stringResource(R.string.name)) }
            )
        }
        Row (
            modifier = Modifier.padding(10.dp)
        ){
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = url,
                onValueChange = {
                    url = it
                },
                visualTransformation = PrefixTransformation("https://"),
                label = { Text(stringResource(R.string.url)) },
            )
        }
        Row (
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ){
            Button(
                modifier = Modifier
                    .padding(20.dp).fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(252, 191, 73)),
                border = BorderStroke(width = 3.dp, color = Color(0, 48, 73)),
                onClick = {
                    if (("https://" + url).length >= 12 &&
                        URLUtil.isValidUrl("https://" + url) &&
                        Patterns.WEB_URL.matcher("https://" + url).matches()){
                        viewModel.addPortal(PortalViewModel.Portal(name, "https://" + url))
                        navController.popBackStack()
                        name = ""
                        url = ""
                    } else{
                        Toast.makeText(context, context.getString(R.string.invalid), Toast.LENGTH_SHORT).show()
                    }
                }
            ){
                Text(text = context.getString(R.string.add),
                    color = Color(0, 48, 73),
                    )
            }
        }
    }
}

class PrefixTransformation(val prefix: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return PrefixFilter(text, prefix)
    }
}

fun PrefixFilter(number: AnnotatedString, prefix: String): TransformedText {

    var out = prefix + number.text
    val prefixOffset = prefix.length

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return offset + prefixOffset
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset < prefixOffset) return 0
            return offset - prefixOffset
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}