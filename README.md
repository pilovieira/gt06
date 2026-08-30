# GT06 Tracker

![Platform](https://img.shields.io/badge/platform-Android-3DDC84?logo=android&logoColor=white)
![Min SDK](https://img.shields.io/badge/minSdk-21-blue)
![Target SDK](https://img.shields.io/badge/targetSdk-34-blue)
![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)
![License](https://img.shields.io/badge/license-unlicensed-lightgrey)

Android app to control and monitor GT06 / TK100 GPS trackers over SMS — no
carrier data plan required on the tracker's side, and no third-party
platform account needed.

## Features

- **Location** — request the tracker's current position by phone call or SMS
- **Lock / unlock** — cut or restore the vehicle's ignition remotely
- **Tracker mode** — switch between monitor and tracker reporting modes
- **Geo-fence** — activate/cancel a radius-based alarm
- **Overspeed alarm** — activate/cancel a speed-threshold alarm
- **ACC alarm** — activate/cancel the ignition-triggered alarm
- **Device configuration** — change password, authorize/remove numbers,
  adjust timezone, set APN, set server IP/port, restart, factory reset
- **Server log** — history of commands sent and responses received
- **Location history** — past positions plotted on a map

## Tech stack

- Java, Android Views (ViewBinding)
- [OrmLite](https://ormlite.com/) for local persistence
- Google Play Services (Ads, Maps)
- Gradle 8.9 / Android Gradle Plugin 8.5.2

## Requirements

- Android Studio (Koala or newer recommended)
- JDK 17
- Android SDK Platform 34

## Getting started

```bash
git clone git@github.com:pilovieira/gt06.git
cd gt06
./gradlew assembleDebug
```

Or open the project in Android Studio and run the `app` configuration on a
device or emulator.

### Useful Gradle tasks

| Task | Description |
| --- | --- |
| `./gradlew assembleDebug` | Build the debug APK |
| `./gradlew assembleRelease` | Build the release APK |
| `./gradlew installDebug` | Install the debug build on a connected device/emulator |
| `./gradlew test` | Run unit tests |
| `./gradlew connectedAndroidTest` | Run instrumented tests |

## Project structure

```
app/src/main/java/br/com/pilovieira/gt06/
├── business/     # Command building, GT06 SMS protocol
├── comm/         # SMS sending/receiving
├── log/          # Server log screen and storage
├── location/     # Location history map screen
├── persist/      # OrmLite DAOs, preferences
└── view/         # Activities and fragments
```

## Contributing

Issues and pull requests are welcome. Please open an issue describing the
change before submitting a large PR.

## License

No license has been specified for this project yet. All rights reserved by
the author unless stated otherwise.
