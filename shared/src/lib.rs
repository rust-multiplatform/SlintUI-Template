#![allow(clippy::all)]

#[cfg(test)]
mod tests;

// Takes a bit to load unfortunately and must happen here / at the start to load properly.
slint::include_modules!();

pub fn entrypoint() {
    println!("Hello from Rust!");

    HelloWorld::new().run();
}
