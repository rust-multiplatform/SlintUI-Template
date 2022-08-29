use glob::glob;

fn main() {
    // Rerun if build.rs changed
    println!("cargo:rerun-if-changed=build.rs");

    // Rerun if any Slint-UI file was changed
    for entry in glob("ui/**/*").expect("Failed to read glob pattern") {
        match entry {
            Ok(path) => println!("cargo:rerun-if-changed={:?}", path.display()),
            Err(_) => (),
        }
    }

    // ---

    // Compile Slint-UI
    slint_build::compile("ui/hello.slint").unwrap();
}
