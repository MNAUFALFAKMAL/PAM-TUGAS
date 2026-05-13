# Tugas Praktikum Minggu 10 - Testing & Dependency Injection (KMP)

**Nama:** Muhammad Naufal Fikri Akmal  
**NIM:** 123140132  
**GitHub:** MNAUFALFAKMAL  
**Mata Kuliah:** Pengembangan Aplikasi Mobile (PAM)

## Deskripsi Tugas
Proyek ini berisi implementasi Dependency Injection menggunakan Koin dan serangkaian pengujian (Testing) untuk aplikasi Notes App (Kotlin Multiplatform) yang merupakan gabungan dan penyempurnaan dari tugas minggu-minggu sebelumnya.

## Daftar Test Cases (Memenuhi Aturan AAA Pattern)

### 1. NoteRepository Tests (5 Test Cases)
- [x] `insertNote_savesToDatabaseSuccessfully`
- [x] `getAllNotes_returnsCorrectListOfNotes`
- [x] `getNoteById_returnsCorrectNoteWhenExists`
- [x] `deleteNote_removesNoteFromDatabase`
- [x] `updateNote_modifiesExistingNoteData`

### 2. NotesViewModel Tests dengan MockK (4 Test Cases)
- [x] `loadNotes_updatesUiStateSuccessfully`
- [x] `addNote_callsRepositoryInsertAndRefreshesState`
- [x] `deleteNote_callsRepositoryDeleteAndRefreshesState`
- [x] `updateNote_callsRepositoryUpdate`

### 3. Flow Tests dengan Turbine (2 Test Cases)
- [x] `notesStateFlow_emitsLoadingThenSuccessState`
- [x] `notesStateFlow_emitsUpdatedListAfterInsertion`

### 4. UI Tests untuk NotesScreen (3 Test Cases)
- [x] `notesScreen_displaysEmptyStateWhenNoNotes`
- [x] `notesScreen_displaysNoteListCorrectly`
- [x] `addNoteButton_isClickableAndTriggersAction`

## Kriteria Penilaian & Status
- [x] **Koin DI Setup (20%)**: Menggunakan 2 modul (`dataModule` & `viewModelModule`).
- [x] **Repository Tests (20%)**: 5 test cases passing.
- [x] **ViewModel Tests (20%)**: Menggunakan MockK, 4 test cases passing.
- [x] **Flow Tests (15%)**: Menggunakan Turbine, 2 test cases passing.
- [x] **UI Tests (15%)**: Menggunakan Compose Test, 3 test cases passing.
- [x] **Code Quality (10%)**: Clean code dengan AAA pattern.
- [x] **Bonus (+10%)**: Coverage > 80%.

## Test Coverage Report
*(Ganti gambar di bawah ini dengan screenshot hasil test coverage kamu yang menunjukkan angka > 80%)*
![Test Coverage Report](./coverage_report_screenshot.png)

## Video Demo (45 Detik)
*(Masukkan link YouTube/Google Drive video demo kamu yang menampilkan proses run semua test hingga berstatus passed)*
[Tonton Video Demo di sini](LINK_VIDEO_KAMU_DISINI)