package com.example.level3_task_22

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level3_task_22.ui.theme.LEVEL3_TASK_22Theme
import com.example.level3_task_22.ui.theme.screens.AddPortalScreen
import com.example.level3_task_22.ui.theme.screens.PortalOverviewScreen
import com.example.level3_task_22.ui.theme.screens.PortalScreens

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LEVEL3_TASK_22Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    PortalNavHost(navController = navController, modifier = Modifier)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
private fun PortalNavHost(
    navController: NavHostController, modifier: Modifier
) {
    val viewModel: PortalViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = PortalScreens.PortalOverview.name,
        modifier = modifier
    ){
        composable(PortalScreens.PortalOverview.name) {
            PortalOverviewScreen(navController, viewModel)
        }
        composable(PortalScreens.AddPortal.name) {
            AddPortalScreen(navController, viewModel)
        }
    }
}