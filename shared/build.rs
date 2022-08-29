use glob::glob;

fn main() {
    // Rerun if build.rs changed
    println!("cargo:rerun-if-changed=build.rs");

    // Rerun if any Slint-UI file was changed
    if let Ok(path) = entry {
        println!("cargo:rerun-if-changed={:?}", path.display())
    }

    // ---

    // Compile Slint-UI
    slint_build::compile("ui/hello.slint").unwrap();
}
