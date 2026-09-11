# Towfik Music

An ad-free Android music app built with Kotlin and Jetpack Compose, forked from
[Velune by Nikhil](https://github.com/nikhilvishwakarma00/Velune).

## Features

- YouTube Music search and playback
- Background playback, offline caching and downloads
- Lyrics, playlists, library sync and listening statistics
- Material You themes and customizable player settings
- Discord activity integration and shared listening

Requires Android 8.0 (API 26) or newer. Network-backed features depend on their
respective services and availability.

## Build the Android app

1. Clone this repository and open it in a current Android Studio version supporting
   Android Gradle Plugin 9.1.1.
2. Install JDK 21 and Android SDK 36. Set `sdk.dir` in `local.properties` or configure
   `ANDROID_HOME` for command-line builds.
3. Sync Gradle, select the `universalDebug` variant, and run on a device or emulator.

```sh
git clone https://github.com/manishakhatun869-creator/Towfik-music.git
cd Towfik-music
./gradlew :app:assembleUniversalDebug
```

The debug APK is written to `app/build/outputs/apk/universal/debug/`.
The **Build Towfik Music APK** GitHub Actions workflow also builds a debug APK on
pull requests to `main`, pushes to `main`, or manual dispatch.

Optional integrations read `LASTFM_API_KEY`, `LASTFM_SECRET`, and
`TOGETHER_BEARER_TOKEN` from local properties or environment variables. Do not
commit credentials. Release signing requires your own keystore and signing
configuration; debug APKs do not require release credentials.

## Branding and compatibility

The display name is **Towfik Music**. The existing application ID
`com.towfik.music` (debug: `com.towfik.music.debug`), Kotlin namespace, preference
keys and backup format remain unchanged to avoid breaking existing installations
and data. A custom violet-and-cyan T/music-note icon is used for the launcher and app header.

Project and update links target this repository, not the upstream app. In-app
release downloads require published releases here with assets named
`app-universal-release.apk` or `app-<architecture>-release.apk` (e.g. `arm64`).
No release APK is published by the debug build workflow.

## Credits and license

Original Velune development by Nikhil. Thanks to Archivetune (base framework),
Metrolist, InnerTune, Kizzy, SimpMusic and BetterLyrics. Original source attribution
and license notices are preserved.

Licensed under [GPL-3.0](LICENSE). Towfik Music is an independent client, not
affiliated with YouTube or Google. Please support artists through official platforms.

## Midnight violet edition

Towfik Music is maintained by **Towfik Mondal**. The default experience uses a
midnight-navy and violet palette, rounded surfaces and a new home masthead.
Existing custom appearance preferences are respected; disable dynamic colors and
pure black in Appearance to use the branded palette. The GitHub star popup and
its automatic launch-count reminders have been removed.

Icon source: `assets/towfik-music-icon.svg`. Regenerate launcher artwork with
`scripts/generate_brand_icons.py` (requires resvg-py and Pillow).

The home dashboard has compact branding and library/history shortcuts. The added
search box and decorative record banner have been removed. Search uses the
existing activity search interface; results require a `search/{query}` route,
not a bare `search` route. Quick picks, music artwork, playback and menus remain.

## Validation

Run `python3 scripts/check_navigation.py` for targeted source-level navigation
regression checks, and `./gradlew :app:testUniversalDebugUnitTest :app:assembleUniversalDebug`
for the unit tests and APK build. These checks do not replace on-device testing;
see [the device smoke-test checklist](docs/DEVICE_TESTING.md).
