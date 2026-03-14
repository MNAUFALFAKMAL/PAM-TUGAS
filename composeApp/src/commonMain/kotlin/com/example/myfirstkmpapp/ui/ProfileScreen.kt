package com.example.myfirstkmpapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myfirstkmpapp.data.ProfileUiState
import myfirstkmpapp.composeapp.generated.resources.Res
import myfirstkmpapp.composeapp.generated.resources.goku
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onToggleBio: () -> Unit,
    onToggleEdit: () -> Unit,
    onToggleDarkMode: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile App") },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(if (uiState.isDarkMode) "🌙" else "☀️")
                        Spacer(modifier = Modifier.width(8.dp))
                        Switch(
                            checked = uiState.isDarkMode,
                            onCheckedChange = { onToggleDarkMode() }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        containerColor = androidx.compose.ui.graphics.Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            ProfileHeader(name = uiState.name, role = uiState.role)

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onToggleBio,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = if (uiState.isBioVisible) "Tutup Bio" else "Buka Bio")
                }

                OutlinedButton(
                    onClick = onToggleEdit,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = if (uiState.isEditing) "Batal Edit" else "Edit Profile")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(visible = uiState.isEditing) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Edit Profile", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(8.dp))

                        StatelessEditField(label = "Name", value = uiState.name, onValueChange = onNameChange)
                        Spacer(modifier = Modifier.height(8.dp))
                        StatelessEditField(label = "Bio", value = uiState.bio, onValueChange = onBioChange)
                        StatelessEditField(label = "Email", value = uiState.email, onValueChange = onEmailChange)
                        Spacer(modifier = Modifier.height(4.dp))
                        StatelessEditField(label = "Phone", value = uiState.phone, onValueChange = onPhoneChange)
                        Spacer(modifier = Modifier.height(4.dp))
                        StatelessEditField(label = "Location", value = uiState.location, onValueChange = onLocationChange)

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onToggleEdit, modifier = Modifier.fillMaxWidth()) {
                            Text("Simpan Perubahan")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(visible = uiState.isBioVisible && !uiState.isEditing) {
                BioCard(description = uiState.bio)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Contact Information",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoRowItem(icon = Icons.Default.Email, label = "Email", value = uiState.email)
                InfoRowItem(icon = Icons.Default.Phone, label = "Phone", value = uiState.phone)
                InfoRowItem(icon = Icons.Default.LocationOn, label = "Location", value = uiState.location)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun StatelessEditField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = label != "Bio"
    )
}

@Composable
fun ProfileHeader(name: String, role: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.goku),
                contentDescription = "Foto Profil",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(120.dp).clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        Text(text = role, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
    }
}

@Composable
fun BioCard(description: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "About Me", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Justify)
        }
    }
}

@Composable
fun InfoRowItem(icon: ImageVector, label: String, value: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = label, tint = MaterialTheme.colorScheme.onPrimaryContainer)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
                Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}