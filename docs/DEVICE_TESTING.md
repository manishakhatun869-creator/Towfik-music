# Towfik Music: on-device smoke tests

Target: reported realme 12x 5G, Android 15, plus an emulator if available.
These are manual checks, **not a claim that they have already passed**.
Install the new debug APK over the previous debug build (same package/signing key).
Do not clear app data merely to apply this UI fix.

- Cold launch: home opens; logo appears; no added search box or record banner.
- Home: library and history shortcuts open; system Back returns home.
- Original search interface: enter a query, submit, open results, play a result,
  return, and repeat with spaces/non-Latin characters and an empty query.
- Quick picks: Start mix starts playback; tap a song, pause/resume, open its menu.
- Player: next/previous, seek, queue, shuffle/repeat, background playback,
  notification controls, and headset disconnect.
- Library: create a playlist, add/remove a song, favourite/unfavourite, reopen.
- Downloads: download an available track, enable airplane mode, play it, delete it.
- Settings: open every card and return; change theme; restart to verify persistence.
- Account: signed-out UI, sign-in/library sync (if used), then sign-out.
- Network: offline cold start and interrupted playback should not crash the app.
- Accessibility: large text, screen reader labels, landscape and Back navigation.
- Optional integrations: lyrics, Discord, Last.fm and shared listening require
  their own service/account checks; a successful APK build does not verify them.

Record app commit, Android version, steps, expected/actual result and any crash
stack trace. Do not share account tokens or credentials in crash reports.
