# My Notes App - Kotlin Multiplatform (KMP)
## Tugas Minggu 07: Local Data Storage (SQLDelight & DataStore)

Aplikasi ini merupakan implementasi pengelolaan catatan (Notes) menggunakan **Compose Multiplatform** dengan fokus pada penyimpanan data lokal sesuai materi pertemuan 7 Pengembangan Aplikasi Mobile (PAM) ITERA. Aplikasi mendukung operasi CRUD (Create, Read, Update, Delete) yang persisten menggunakan **SQLDelight** dan pengelolaan preferensi menggunakan **Multiplatform Settings**.

### 🔍 Deskripsi Tugas & Materi 7
Berdasarkan rubrik penilaian dan materi pertemuan 7, proyek ini mencakup:
1.  **SQLDelight:** Implementasi database type-safe untuk penyimpanan catatan secara permanen di perangkat menggunakan SQLite.
2.  **Repository Pattern:** Abstraksi data layer untuk memisahkan logika bisnis dengan sumber data (Local Storage).
3.  **DataStore / Multiplatform Settings:** Penyimpanan preferensi pengguna seperti tema (Light/Dark/System) dan urutan pengurutan (Newest/Oldest).
4.  **KMP Architecture:** Berbagi logika kode (CommonMain) antara Android dan platform lainnya menggunakan pola Expect/Actual untuk Driver Database.

---

### 🛠️ Fitur Utama
-   **Full CRUD:**
  -   **Create:** Menambah catatan baru dengan judul dan isi.
  -   **Read:** Menampilkan daftar catatan secara real-time menggunakan `Flow`.
  -   **Update:** Mengubah isi catatan yang sudah ada melalui layar editor.
  -   **Delete:** Menghapus catatan langsung dari database melalui tombol di setiap item catatan.
-   **Pencarian (Search):** Mencari catatan secara dinamis berdasarkan kata kunci pada judul atau konten menggunakan query `LIKE`.
-   **Manajemen Tema:** Pilihan tema **Light**, **Dark**, atau **System** dengan aksen warna Pink sesuai preferensi UI.
-   **Pengurutan (Sorting):** Fitur untuk mengurutkan catatan berdasarkan waktu update (Terbaru atau Terlama).
-   **UI Responsif:** Dibangun menggunakan Material Design 3 dengan Scaffold, FloatingActionButton, dan ModalNavigationDrawer.

---

### 🏗️ Arsitektur Kode
Proyek ini mengikuti pola arsitektur yang direkomendasikan pada materi kuliah:
-   **`commonMain`**: Berisi logika utama aplikasi.
  -   `data/local`: Definisi `DatabaseDriverFactory` (Expect/Actual).
  -   `data/repository`: `NoteRepository` sebagai mediator antara UI dan data source.
  -   `data/settings`: `SettingsManager` menggunakan library `multiplatform-settings` (alternatif DataStore di KMP).
  -   `viewmodel`: `NotesViewModel` mengelola state UI menggunakan `StateFlow`.
  -   `screens`: UI Components (NotesScreen, NoteEditorScreen, SettingsScreen).
-   **`sqldelight`**: Berisi file `Note.sq` yang mendefinisikan skema tabel dan query SQL.

---

### 📸 Screenshots
Berikut adalah tampilan antarmuka :

| Daftar Note | Tambah/Edit Note | Settings  |
| :---: | :---: |:---------:|
| <img width="341" height="742" alt="Image" src="https://github.com/user-attachments/assets/33467a7d-02a0-432f-b1a0-138475941714" /> | <img width="341" height="742" alt="Image" src="https://github.com/user-attachments/assets/9a436027-4cbc-401c-92c9-cb87bdcc09de" /> | <img width="252" height="554" alt="Image" src="https://github.com/user-attachments/assets/4bc95bd4-4cbe-49c3-b8a1-d1b1091be615" />          |

---

### 🎥 Demo Video
Untuk melihat demonstrasi fitur CRUD (Create, Read, Update, Delete), fitur pencarian, serta perpindahan tema secara real-time, silakan klik tautan di bawah ini:

👉 **https://drive.google.com/file/d/1lQqOhiyD1GuAHbCrYA7EO8vepheJ08LQ/view?usp=sharing**