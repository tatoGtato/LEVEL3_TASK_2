package com.example.level3_task_22.ui.theme.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.level3_task_22.PortalViewModel
import com.example.level3_task_22.R

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortalOverviewScreen(navController: NavHostController, viewModel: PortalViewModel) {
    val context = LocalContext.current
    val localDensity = LocalDensity.current
    var HeightDp by remember { mutableStateOf(0.dp) }
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
        content = {
                innerPadding -> PortalOverview(Modifier.padding(innerPadding),
                                                navController,
                                                viewModel,
                                                HeightDp)
                  },
        floatingActionButton = { AddPortal(navController) }
    )
}

@Composable
fun PortalOverview(padding: Modifier,
                   navController: NavHostController,
                   viewModel: PortalViewModel,
                   Hei: Dp
                   ) {
    val context = LocalContext.current
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Hei)
    ){
        LazyVerticalGrid(
            modifier = Modifier.padding(15.dp),
            columns = GridCells.Adaptive(minSize = 128.dp)
        ) {
            items(
                items = viewModel.portals,
                itemContent = { item ->
                    PortalCard(item, context)
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PortalCard(item: PortalViewModel.Portal, context: Context) {
    Card(
        onClick = { openTab(context, item.url)},
        modifier = Modifier
            .padding(8.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp),
    ) {
        Text(
            text = item.name,
            Modifier.padding(16.dp)
        )
        Text(
            text = item.url,
            Modifier.padding(16.dp)
        )
    }
}

@Composable
fun AddPortal(navController: NavHostController) {
    FloatingActionButton(
        onClick = {
        navController.navigate(PortalScreens.AddPortal.name)
        },
        containerColor = Color(252, 191, 73)
    ){
        Icon(Icons.Filled.Add, "",
            tint = Color(0, 48, 73))
    }
}

fun openTab(context: Context, URL: String) {
    // on below line we are creating a variable for
    // package name and specifying package name as
    // package of chrome application.
    val package_name = "com.android.chrome"

    // on below line we are creating a variable
    // for the activity and initializing it.
    val activity = (context as? Activity)

    // on below line we are creating a variable for
    // our builder and initializing it with
    // custom tabs intent
    val builder = CustomTabsIntent.Builder()

    // on below line we are setting show title
    // to true to display the title for
    // our chrome tabs.
    builder.setShowTitle(true)

    // on below line we are enabling instant
    // app to open if it is available.
    builder.setInstantAppsEnabled(true)

    // on below line we are setting tool bar color for our custom chrome tabs.
    builder.setToolbarColor(ContextCompat.getColor(context, R.color.black))

    // on below line we are creating a
    // variable to build our builder.
    val customBuilder = builder.build()

    // on below line we are checking if the package name is null or not.
    if (package_name != null) {
        // on below line if package name is not null
        // we are setting package name for our intent.
        customBuilder.intent.setPackage(package_name)

        // on below line we are calling launch url method
        // and passing url to it on below line.
        customBuilder.launchUrl(context, Uri.parse(URL))
    } else {
        // this method will be called if the
        // chrome is not present in user device.
        // in this case we are simply passing URL
        // within intent to open it.
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(URL))

        // on below line we are calling start
        // activity to start the activity.
        activity?.startActivity(i)
    }

}