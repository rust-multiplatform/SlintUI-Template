# Crate/Library: Shared

[![Rust](https://github.com/rust-multiplatform/Windowing-Template/actions/workflows/shared.yml/badge.svg)](https://github.com/rust-multiplatform/Windowing-Template/actions/workflows/shared.yml)

This is our **one** crate that holds all the shared code for our Cross/Multi-platform apps.  
This crate will be compiled as a library by each platform in their target architecture and called from each platform.

## How does this work?

Rust is a cross-platform language that supports many different architectures and platforms.  
Building for Desktop Platforms (like Windows, Linux, macOS) is natively supported as long as the required tools are installed.
For Android, iOS and WebAssembly (WASM) we _"require"_ some additional tools to build.
In essence, we could do it without those tools but it would be much harder to do so.
Said tools commonly package our apps into .APK, .APP and .JS/WASM, link them against our library and make sure the entrypoint is called.

Inside `src/lib.rs` we have a public entrypoint defined:

```rust
pub fn entrypoint() {
    // Our cross-platform code here
}
```

Each platform calls this, while each platform itself has an entrypoint.  

Ideally, we **only** have our shared code.  
However, sometimes we need some very platform specific things that can only be done on said platform.
In this case we have _some_ non-shared/platform-specific code but overall this should only be true for very special cases as the Rust standard library works mostly the same on each platform/target.

## What does this crate do after being called?

A simple window is opened following the example on [winit's docs](https://docs.rs/winit/latest/winit/).  
This is an empty window with nothing in it.
The process (apps) stays open until the app is exited (the `X` on most Desktop Systems or killing it on mobile).

![Platform Windows](.github/images/platform_windows.png "Platform Windows")

## Building

|                         | Host: Windows                                                                                                                                                                                                                                                    | Host: Linux                                                                                                                                                                     | Host: macOS                                                                                                                                    |
| ----------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------- |
| **Target: Windows**     | ✅: [Visual Studio](https://visualstudio.com/)                                                                                                                                                                                                                    | 🔀: [MinGW](https://www.mingw-w64.org/)                                                                                                                                          | 🔀: [MinGW](https://www.mingw-w64.org/)                                                                                                         |
| **Target: Linux**       | ⚠️: [WSL](https://docs.microsoft.com/en-us/windows/wsl/) or VM or Docker                                                                                                                                                                                          | ✅: [GCC](https://gcc.gnu.org/) or [Clang](https://clang.llvm.org/)                                                                                                              | 🔀: Docker or VM                                                                                                                                |
| **Target: macOS**       | ⚠️: [Docker-OSX (inside WSL with Docker)](https://github.com/sickcodes/Docker-OSX) or [OSX-KVM (inside WSL or VM)](https://github.com/kholia/OSX-KVM) or [macOS-VirtualBox (inside/with WSL and/or MSYS2/MinGW)](https://github.com/myspaghetti/macos-virtualbox) | ⚠️: [Docker-OSX](https://github.com/sickcodes/Docker-OSX) or [OSX-KVM](https://github.com/kholia/OSX-KVM) or [macOS-VirtualBox](https://github.com/myspaghetti/macos-virtualbox) | ✅: [XCode](https://developer.apple.com/xcode/)                                                                                                 |
| **Target: Android**     | 🔀: [Android Studio](https://developer.android.com/studio/) or [Android CommandLine-Tools](https://developer.android.com/studio/#command-tools)                                                                                                                   | 🔀: [Android Studio](https://developer.android.com/studio/) or [Android CommandLine-Tools](https://developer.android.com/studio/#command-tools)                                  | 🔀: [Android Studio](https://developer.android.com/studio/) or [Android CommandLine-Tools](https://developer.android.com/studio/#command-tools) |
| **Target: iOS**         | ⚠️: [Docker-OSX (inside WSL with Docker)](https://github.com/sickcodes/Docker-OSX) or [OSX-KVM (inside WSL or VM)](https://github.com/kholia/OSX-KVM) or [macOS-VirtualBox (inside/with WSL and/or MSYS2/MinGW)](https://github.com/myspaghetti/macos-virtualbox) | ⚠️: [Docker-OSX](https://github.com/sickcodes/Docker-OSX) or [OSX-KVM](https://github.com/kholia/OSX-KVM) or [macOS-VirtualBox](https://github.com/myspaghetti/macos-virtualbox) | ✅: [XCode](https://developer.apple.com/xcode/)                                                                                                 |
| **Target: WebAssembly** | ✅: [Wasm-Pack](https://rustwasm.github.io/wasm-pack/installer/)                                                                                                                                                                                                  | ✅: [Wasm-Pack](https://rustwasm.github.io/wasm-pack/installer/)                                                                                                                 | ✅: [Wasm-Pack](https://rustwasm.github.io/wasm-pack/installer/)                                                                                |

✅ = Natively supported.  
🔀 = Cross-Compilation & Toolchain needed.  
⚠️ = Possible, but takes some more effort and/or special setups or VM to work.

Requirements:  

- Rust and Cargo is installed, preferably via [Rustup](https://rustup.rs/). Use the stable channel ideally.

Debug/Development Build:

```shell
cargo build --package shared
```

Release/Production Build:

```shell
cargo build --package shared --release
```

> Optionally add `--verbose` to see what is happening.  
> Floods your console with message though possibly, use when there are build errors.

## Testing

Requirements:  

- Rust and Cargo is installed, preferably via [Rustup](https://rustup.rs/). Use the stable channel ideally.

Debug/Development Testing:

```shell
cargo test --package shared
```

Release/Production Testing:

```shell
cargo test --package shared --release
```

> Optionally add `--verbose` to see what is happening.  
> Floods your console with message though possibly, use when there are build errors.
