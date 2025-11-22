package com.example.lab_week_11_a

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab_week_11_a.ui.theme.LAB_WEEK_11_ATheme

class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by lazy {
        val application = application as SettingsApplication
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return SettingsViewModel(application.settingsStore) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }).get(SettingsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LAB_WEEK_11_ATheme {
                MainScreen(settingsViewModel = settingsViewModel)
            }
        }
    }
}

@Composable
fun MainScreen(settingsViewModel: SettingsViewModel) {
    val savedText by settingsViewModel.userTextFlow.collectAsState(initial = "")
    var currentInput by remember { mutableStateOf(savedText) }

    // Update currentInput when savedText changes (e.g., on app start)
    LaunchedEffect(savedText) {
        if (currentInput.isEmpty()) { // Only update if input is empty, to avoid overwriting user's typing
            currentInput = savedText
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(text = "Saved Text:", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = savedText, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = currentInput,
                onValueChange = { currentInput = it },
                label = { Text("Enter text here") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { settingsViewModel.saveText(currentInput) }) {
                Text("Save Text")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LAB_WEEK_11_ATheme {
        // For preview, we can pass a dummy ViewModel or create a mock
        // This is a simplified preview and might not fully represent the runtime behavior
        MainScreen(settingsViewModel = SettingsViewModel(SettingsStore(object : android.content.ContextWrapper(null) {})))
    }
}
