# 📱 Admob Ads Implementation

This Android project provides a modular implementation of all major AdMob ad formats, controlled via Firebase Remote Config. It supports:

- ✅ Adaptive & Collapsible Banners
- ✅ Native Ads
- ✅ Interstitial Ads
- ✅ Rewarded Ads
- ✅ Rewarded Interstitial Ads
- ✅ App Open Ads
- ✅ UMP consent, shared fullscreen-ad gate (`:gmaAds`)

---

## ✅ Key Functionalities Handled

- Firebase Remote Config for ad control (enable/disable ads remotely)
- Premium user check to disable ads for subscribed users
- Internet connectivity verification before showing ads
- Activity lifecycle safety (prevent loading ads in destroyed/finishing activities)
- Ad ID validation (ensure non-empty ad unit IDs)
- Collapsible banner position handling (top/bottom)
- Auto adaptive banner sizing based on screen dimensions
- Ad listener callbacks (loaded, failed, clicked, impression, closed)
- Placeholder visibility management for smooth UI experience
- Preloading logic for interstitial, rewarded, and rewarded interstitial ads

---

## 🧩 Supported Ad Types

- **Banner Ads**: Adaptive and collapsible formats with smart placement.
- **Native Ads**: Configurable templates with UI component support.
- **Interstitial Ads**: Shown between screen transitions or actions.
- **Rewarded Ads**: Offers user incentives.
- **Rewarded Interstitial Ads**: Combines interstitial and rewarded experiences.
- **App Open Ads**: Displayed during app cold/warm start.

---

## 🗂️ Project Structure

| Module     | Package                          | Purpose                                                                                                    |
|------------|----------------------------------|------------------------------------------------------------------------------------------------------------|
| `:app`     | `com.orbitalsonic.admobads`      | Original sample: single-module ads implementation (banner, native, interstitial, rewarded, app open).      |
| `:gmaApp`  | `com.hypersoft.ads.practice`     | Demo app for the modular `:gmaAds` library (startup, language, onboarding, dashboard, feature screens).   |
| `:gmaAds`  | `com.hypersoft.ads.practice.gmaAds` | Reusable ads library: config → validation → controller pipeline per format, UMP consent, Koin module. |
| `:data`    | `com.hypersoft.ads.practice.data`   | Remote Config and SharedPreferences used by `:gmaAds`.                                                  |
| `:core`    | `com.hypersoft.ads.practice.core`   | Shared base classes, extensions, internet manager, theme.                                               |

`:gmaAds` is taken from [hypersoftdev/Admob-Ads](https://github.com/hypersoftdev/Admob-Ads). See [`gmaAds/README.md`](gmaAds/README.md) for how to add or remove a placement and the load/show call shapes, and [`docs/architecture.md`](docs/architecture.md) for the architecture.

---

## 🛠️ Build Setup

- Gradle **9.8.2** (wrapper), Android Gradle Plugin **9.4.1**, Kotlin **2.4.21**
- Kotlin DSL (`*.gradle.kts`) with a version catalog: `gradle/libs.versions.toml`
- Java **21** source/target; run Gradle on JDK 21 or newer (Android Studio's bundled JBR works)
- `compileSdk` 37, `minSdk` 24

```bash
./gradlew :app:assembleDebug      # original sample
./gradlew :gmaApp:assembleDebug   # modular :gmaAds demo
./gradlew :gmaApp:installDebug    # install on a connected device
```

In Android Studio pick the `app` or `gmaApp` run configuration.

**Firebase:** `:app` ships a `google-services.json`. `:gmaApp` has none, so Remote Config falls back to its built-in defaults (all placements on). Add a `google-services.json` for `com.hypersoft.ads.practice` and apply the `google-services` plugin to `:gmaApp` to control ads remotely.

**Ad IDs:** all IDs are Google test IDs. Replace the `resValue` entries in `app/build.gradle.kts` and `gmaAds/build.gradle.kts` before release.

---

## 📄 License

Copyright 2022 OrbitalSonic

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

---

## 🙌 Contributions

Contributions and pull requests are welcome. Please ensure to test and document changes.

> **Note:** Always test ads using test IDs during development. Only use real IDs in production.
