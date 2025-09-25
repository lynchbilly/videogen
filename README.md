# 🧙‍♂️ Videogen: Magic Photo-to-Lipsync Video Generator

Bring photos to life—turn any image into a speaking, lip-synced video using your own voice!  
**Videogen** combines Python machine learning (Wav2Lip), Android development, and Chaquopy to make this possible—no deep learning experience required.

---

## Table of Contents

1. [What Is Videogen?](#what-is-videogen)
2. [Prerequisites](#prerequisites)
3. [Step-by-Step Setup Guide](#step-by-step-setup-guide)
    - [A. Clone the Repository](#a-clone-the-repository)
    - [B. Android Studio Setup](#b-android-studio-setup)
    - [C. Install Chaquopy](#c-install-chaquopy)
    - [D. Get Wav2Lip Model & Scripts](#d-get-wav2lip-model--scripts)
    - [E. Prepare Python Dependencies](#e-prepare-python-dependencies)
    - [F. Configure Android Permissions](#f-configure-android-permissions)
    - [G. Place Model File on Device](#g-place-model-file-on-device)
    - [H. Build and Run](#h-build-and-run)
4. [How to Use Videogen](#how-to-use-videogen)
5. [Troubleshooting](#troubleshooting)
6. [FAQ](#faq)
7. [Resources](#resources)
8. [Contributing](#contributing)

---

## What Is Videogen?

Videogen lets you:
- Select a photo (portrait recommended)
- Record or choose an audio file
- Generate a video where the photo’s lips sync to your voice

**Powered by:**  
- Wav2Lip (deep learning model for lip sync)  
- Chaquopy (Python in Android)  
- Android Studio (app development)

---

## Prerequisites

- **Android Studio** (latest version)  
  [Download here](https://developer.android.com/studio)
- **Chaquopy plugin** ([Docs](https://chaquo.com/chaquopy/))
- **Wav2Lip model & code**  
  [Wav2Lip GitHub](https://github.com/Rudrabha/Wav2Lip)
- **Python 3.7+** (for model compatibility)
- **A test device or emulator** (Android 8.0+, 2GB+ free storage)
- **Good, clear portrait images**

---

## Step-by-Step Setup Guide

### A. Clone the Repository

```bash
git clone https://github.com/lynchbilly/videogen.git
cd videogen
```
Or open in Android Studio:  
**File > Open > Select 'videogen' folder**

---

### B. Android Studio Setup

1. **Open Android Studio.**
2. **Open/Import Project:**  
   - File > Open > Select your cloned `videogen` directory.
3. **Wait for Gradle sync.**

---

### C. Install Chaquopy

1. **Open `app/build.gradle`.**
2. **Add Chaquopy plugin:**

```gradle
plugins {
    id 'com.android.application'
    id 'com.chaquo.python' version '12.0.0'
}
```

3. **Add Python source folder to your Gradle config:**

```gradle
python {
    // Path to your Python scripts
    srcDir = "src/main/python"
    buildPython = "python3"
}
```
4. **Sync Project**  
   - Click "Sync Now" if prompted.

---

### D. Get Wav2Lip Model & Scripts

1. **Download Wav2Lip repo:**
   - [https://github.com/Rudrabha/Wav2Lip](https://github.com/Rudrabha/Wav2Lip)
2. **Copy the following files to `app/src/main/python/`:**
   - `inference.py`
   - `models.py`
   - `face_detection.py`
   - `hparams.py`
   - Any other .py files `inference.py` imports
3. **Check imports in `inference.py`**  
   - Make sure all dependencies are present in your folder.

---

### E. Prepare Python Dependencies

1. **Create or edit `requirements.txt`**:

```txt name=requirements.txt
numpy
torch
opencv-python
face-alignment
scipy
matplotlib
audioread
pillow
```
2. **Check Chaquopy compatibility:**  
   - Some packages may require specific versions or wheels.  
   - See [Supported Packages](https://chaquo.com/chaquopy/doc/current/packages.html)
3. **To install additional wheels:**  
   - Place compatible `.whl` files in `app/src/main/python/` if needed.

---

### F. Configure Android Permissions

Add these permissions to `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.RECORD_AUDIO"/>
```
Request permissions at runtime in your app code if targeting Android 6.0+.

---

### G. Place Model File on Device

1. **Download `wav2lip_gan.pth`:**
   - [Direct link](https://github.com/Rudrabha/Wav2Lip#pre-trained-model)
2. **Transfer to device/emulator:**
   - Connect via USB; use Android File Transfer, ADB, or drag-drop in emulator.
   - Place in `/sdcard/` (external storage) or your app’s private storage.
   - Example ADB command:
     ```bash
     adb push wav2lip_gan.pth /sdcard/wav2lip_gan.pth
     ```

---

### H. Build and Run

1. **Build the project:**  
   - Click “Build > Make Project”
2. **Run the app:**  
   - Click the green "Play" button.
   - Select your emulator/device.
3. **First Launch:**  
   - Grant all requested permissions.

---

## How to Use Videogen

1. **Open Videogen on your device.**
2. **Select a photo** (frontal face, clear image for best results).
3. **Record or select an audio file** (your voice, short speech recommended).
4. **Tap "Generate Video".**
5. **Wait for progress bar.**
6. **View the generated video.**
   - Share/save as desired.

---

## Troubleshooting

### App Won’t Build
- **Check Gradle sync:** All dependencies installed?
- **Chaquopy error:** Ensure plugin version matches docs.
- **Python package missing:** Add to `requirements.txt`.

### App Crashes on Video Generation
- **Model file missing:** Is `wav2lip_gan.pth` in `/sdcard/`?
- **Permissions:** Did you grant storage/audio?
- **Logcat shows ImportError:** Check all Wav2Lip Python files are present.

### No Face Detected / Poor Output
- Use clear, frontal images.
- Try different lighting/backgrounds.

### Progress Bar Stuck
- Model file too large for device/emulator RAM—try on physical device or free up space.

### Dependency Errors
- Some Python packages may not run on Android/Chaquopy.  
  - Use [Supported Packages](https://chaquo.com/chaquopy/doc/current/packages.html)
  - Try older versions if needed.

---

## FAQ

**Q: Can I use this on Windows/Mac/Linux?**  
A: This repo is for Android, but you can run Wav2Lip separately on desktop.

**Q: How big is the model file?**  
A: ~400MB. Needs plenty of free space.

**Q: Can I use other voices or images?**  
A: Yes—any portrait image and WAV/MP3 audio.

**Q: Is internet required?**  
A: No, everything runs locally.

---

## Resources

- [Wav2Lip GitHub](https://github.com/Rudrabha/Wav2Lip)
- [Chaquopy Docs](https://chaquo.com/chaquopy/)
- [Android Studio Setup](https://developer.android.com/studio)
- [Face Alignment Python](https://pypi.org/project/face-alignment/)

---

## Contributing

PRs welcome!  
Open issues for bugs, feature requests, or questions.

---

**Mischief Managed!**

---