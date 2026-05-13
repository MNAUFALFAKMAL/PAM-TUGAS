package com.example.myfirstkmpapp.data

data class ProfileUiState(
    val name: String = "Goku",
    val role: String = "MC Anime Dragon Ball",
    val bio: String = "Saya adalah MC Anime Dragon Ball. Saya lahir tanggal 22 Oktober. Sudah berkeluarga dan pernah melawan Frieza. IZIIINNN🙏🙏🙏.",
    val email: String = "goku@dragonball.ac.id",
    val phone: String = "+62 812-3456-7890",
    val location: String = "Gunung Paozu",
    val isBioVisible: Boolean = true,
    val isDarkMode: Boolean = false,
    val isEditing: Boolean = false
)