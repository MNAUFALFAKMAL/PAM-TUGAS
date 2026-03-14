# Tugas 3 - Pengembangan Aplikasi Mobile - My Profile App
- **Nama:** Muhammad Naufal Fikri Akmal
- **NIM:** 123140132
- **Program Studi:** Teknik Informatika
- **Kelas:** Pengembangan Aplikasi Mobile RB Minggu 3

## Tampilan Aplikasi (Screenshot)
<img width="428" height="936" alt="image" src="https://github.com/user-attachments/assets/34179202-04e5-4c56-8482-e4774bd3e086" />

---

# Tugas 4 - Refactoring: MVVM Architecture & State Management

Pada rilis terbaru ini, aplikasi profil statis telah dirombak secara menyeluruh menggunakan pola arsitektur **MVVM (Model-View-ViewModel)**. Aplikasi kini bersifat dinamis, reaktif, dan memiliki struktur kode yang jauh lebih *maintainable*.

## Tampilan Fitur Terbaru

| Mode Terang (Default) | Form Edit (Real-time) | Mode Gelap (Smooth Animasi) |
| :---: | :---: | :---: |
| <img width="250" src="https://github.com/user-attachments/assets/7a466a0a-edf5-4c52-86c4-1e69671fd608" /> | <img width="250" src="https://github.com/user-attachments/assets/17c22e2a-2cfc-4b7c-ad21-9b1bb5f1de70" /> | <img width="250" src="https://github.com/user-attachments/assets/f232e568-f050-4758-a17f-cbb5da5aeb6c" /> |

---

## Fitur & Implementasi Teknis

Sebagai pengembang, saya menerapkan beberapa standar pengembangan modern pada aplikasi Compose Multiplatform ini:

### 1. Manajemen State Terpusat (UI State Pattern)
Seluruh data yang dapat berubah di layar tidak lagi dideklarasikan secara acak. Semua *state* dibungkus ke dalam sebuah `data class` tunggal bernama `ProfileUiState`. Hal ini mencakup teks (Nama, Bio, Email, Telepon, Lokasi) maupun status visibilitas komponen (sedang diedit, mode gelap, dll).

### 2. Arsitektur Berbasis ViewModel
Logika bisnis dan penyimpanan data UI telah dipisahkan dari tampilan menggunakan kelas `ProfileViewModel`. Pengelolaan *state* menggunakan **StateFlow** yang bersifat reaktif. Semua tindakan pengguna (*user intent*) diproses melalui fungsi-fungsi *event handler* secara aman menggunakan metode `.update { }`.

### 3. Komponen Reusable & State Hoisting
Aplikasi ini secara ketat menerapkan prinsip **State Hoisting**.
- Layar utama (`ProfileScreen`) dibangun sebagai komponen *Stateless*, yang hanya menerima aliran data dan melempar *callback* ke ViewModel.
- Untuk mencegah penulisan kode berulang pada form input, saya membuat komponen *reusable* bernama `StatelessEditField` yang menangani semua input teks secara seragam.

### 4. Edit Profil Secara Real-Time
Pengguna dapat menekan tombol **Edit Profile** untuk membuka form interaktif (`AnimatedVisibility`). Tidak hanya sekadar nama, pengguna bisa mengubah seluruh data kontak (Email, Telepon, Lokasi). Perubahan ini tervalidasi dan ter-update secara *real-time* tanpa ada jeda pemuatan ulang halaman.

### 5. Smooth Dark Mode Transition
Terdapat fitur *Switch* untuk mengganti tema aplikasi dari mode Terang ke mode Gelap. Untuk meningkatkan pengalaman pengguna (UX), perpindahan tema ini tidak terjadi secara kaku/berkedip, melainkan diiringi dengan **animasi transisi warna yang halus** (*smooth*) menggunakan implementasi `animateColorAsState` dengan durasi 500ms.

---

## Struktur Direktori
Kode telah dikelompokkan ke dalam struktur paket yang terpisah berdasarkan tanggung jawabnya (Separation of Concerns):

```text
com.example.myfirstkmpapp
│
├── data/
│   └── ProfileUiState.kt      # Model data untuk State UI
├── viewmodel/
│   └── ProfileViewModel.kt    # Logic bisnis dan pengelolaan StateFlow
├── ui/
│   └── ProfileScreen.kt       # Komponen visual (Stateless UI)
└── App.kt                     # Entry point & Pengaturan Tema Aplikasi