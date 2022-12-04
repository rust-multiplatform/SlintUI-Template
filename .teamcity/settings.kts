import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.triggers.schedule
import jetbrains.buildServer.configs.kotlin.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2022.10"

project {

    buildType(Test)
    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    allowExternalStatus = true
    artifactRules = "target/**/*"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        step {
            name = "Build (Debug)"
            type = "cargo"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            param("cargo-build-package", "platform_linux")
            param("cargo-test-no-default-features", "true")
            param("cargo-toolchain", "stable")
            param("cargo-verbosity", "--verbose")
            param("cargo-bench-package", "platform_linux")
            param("cargo-command", "build")
        }
        step {
            name = "Build (Release)"
            type = "cargo"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            param("cargo-build-package", "platform_linux")
            param("cargo-build-release", "true")
            param("cargo-test-no-default-features", "true")
            param("cargo-toolchain", "stable")
            param("cargo-verbosity", "--verbose")
            param("cargo-bench-package", "platform_linux")
            param("cargo-bench-arguments", "--release")
            param("cargo-command", "build")
        }
    }

    triggers {
        vcs {
        }
        schedule {
            schedulingPolicy = daily {
                hour = 3
            }
            triggerBuild = always()
        }
    }
})

object Test : BuildType({
    name = "Test"

    allowExternalStatus = true
    artifactRules = "target/**/*"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        step {
            name = "Test (Debug)"
            type = "cargo"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            param("cargo-test-no-fail-fast", "true")
            param("cargo-test-package", "platform_linux")
            param("cargo-test-no-default-features", "true")
            param("cargo-toolchain", "stable")
            param("cargo-verbosity", "--verbose")
            param("cargo-command", "test")
        }
        step {
            name = "Test (Release)"
            type = "cargo"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            param("cargo-test-no-fail-fast", "true")
            param("cargo-test-package", "platform_linux")
            param("cargo-test-no-default-features", "true")
            param("cargo-toolchain", "stable")
            param("cargo-verbosity", "--verbose")
            param("cargo-test-release", "true")
            param("cargo-command", "test")
        }
    }

    triggers {
        vcs {
        }
        schedule {
            schedulingPolicy = daily {
                hour = 3
            }
            triggerBuild = always()
        }
    }
})
