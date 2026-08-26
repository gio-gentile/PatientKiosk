package it.GiorgioGentile759951.patientkiosk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import it.GiorgioGentile759951.patientkiosk.ui.screens.PatientKioskApp
import it.GiorgioGentile759951.patientkiosk.ui.screens.WelcomeScreen
import it.GiorgioGentile759951.patientkiosk.ui.theme.PatientKioskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PatientKioskTheme {
                PatientKioskApp()
            }
        }
    }
}