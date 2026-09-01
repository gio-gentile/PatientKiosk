package it.GiorgioGentile759951.patientkiosk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import it.GiorgioGentile759951.patientkiosk.navigation.PatientKioskApp
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