package it.GiorgioGentile759951.patientkiosk.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import it.GiorgioGentile759951.patientkiosk.R

@Composable
private fun SourceItem(
    questionnaire: String,
    source: String,
    onOpenClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    dimensionResource(
                        R.dimen.spacing_md
                    )
                )
        ) {

            Text(
                text = questionnaire,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = source,
                style = MaterialTheme.typography.bodyMedium
            )

            TextButton(
                onClick = onOpenClick
            ) {
                Text(
                    text = stringResource(
                        R.string.open_source
                    )
                )
            }
        }
    }
}

@Composable
fun InfoScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val versionName = context.packageManager
        .getPackageInfo(context.packageName, 0)
        .versionName ?: ""

    fun openUrl(url: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )

        context.startActivity(intent)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    dimensionResource(
                        R.dimen.screen_padding_s
                    )
                ),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(
                    R.dimen.spacing_lg
                )
            )
        ) {

            item {
                Text(
                    text = stringResource(
                        R.string.info_app_title
                    ),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(
                    modifier = Modifier.height(
                        dimensionResource(
                            R.dimen.spacing_sm
                        )
                    )
                )

                Text(
                    text = stringResource(
                        R.string.version_format,
                        versionName
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(
                    modifier = Modifier.height(
                        dimensionResource(
                            R.dimen.spacing_md
                        )
                    )
                )

                Text(
                    text = stringResource(
                        R.string.info_app_description
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                Text(
                    text = stringResource(
                        R.string.sources_title
                    ),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                SourceItem(
                    questionnaire = "DLQI",
                    source = "Cardiff University",
                    onOpenClick = {
                        openUrl(
                            "https://www.cardiff.ac.uk/medicine/resources/quality-of-life-questionnaires/dermatology-life-quality-index"
                        )
                    }
                )
            }

            item {
                SourceItem(
                    questionnaire = "HADS",
                    source = "PubMed — Zigmond & Snaith",
                    onOpenClick = {
                        openUrl(
                            "https://pubmed.ncbi.nlm.nih.gov/6880820/"
                        )
                    }
                )
            }

            item {
                SourceItem(
                    questionnaire = "WHO-5",
                    source = "World Health Organization",
                    onOpenClick = {
                        openUrl(
                            "https://www.who.int/publications/m/item/WHO-UCN-MSD-MHE-2024.01"
                        )
                    }
                )
            }

            item {
                SourceItem(
                    questionnaire = "GAD-7",
                    source = "Spitzer et al.",
                    onOpenClick = {
                        openUrl(
                            "https://jamanetwork.com/journals/jamainternalmedicine/fullarticle/410326"
                        )
                    }
                )
            }

            item {
                SourceItem(
                    questionnaire = "PHQ-9",
                    source = "Kroenke et al.",
                    onOpenClick = {
                        openUrl(
                            "https://onlinelibrary.wiley.com/doi/full/10.1046/j.1525-1497.2001.016009606.x"
                        )
                    }
                )
            }

            item {
                SourceItem(
                    questionnaire = "PSS-10",
                    source = "Carnegie Mellon University",
                    onOpenClick = {
                        openUrl(
                            "https://www.cmu.edu/dietrich/psychology/stress-immunity-disease-lab/scales/html/pss.html"
                        )
                    }
                )
            }

            item {
                Text(
                    text = stringResource(
                        R.string.bibliography_title
                    ),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                Text(
                    text = """
                        Finlay AY, Khan GK.
                        Dermatology Life Quality Index.

                        Zigmond AS, Snaith RP.
                        The Hospital Anxiety and Depression Scale.

                        Spitzer RL, Kroenke K, Williams JBW, Löwe B.
                        A Brief Measure for Assessing Generalized Anxiety Disorder: The GAD-7.

                        Kroenke K, Spitzer RL, Williams JBW.
                        The PHQ-9: Validity of a Brief Depression Severity Measure.

                        Cohen S, Kamarck T, Mermelstein R.
                        A Global Measure of Perceived Stress.
                    """.trimIndent(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                Text(
                    text = stringResource(
                        R.string.disclaimer_title
                    ),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                Text(
                    text = stringResource(
                        R.string.clinical_disclaimer
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                Button(
                    onClick = onBackClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(
                            R.string.close_button
                        )
                    )
                }
            }
        }
    }
}