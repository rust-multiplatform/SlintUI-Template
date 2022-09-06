use crate::entrypoint;

#[test]
#[cfg(feature = "ui-tests")]
fn check_entrypoint_exists() {
    entrypoint();
}
