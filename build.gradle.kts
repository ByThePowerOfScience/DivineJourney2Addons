import org.gradle.plugins.ide.idea.model.IdeaProject
import java.util.stream.Collectors

plugins {
    `java-library`
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.8"
}
group = "btpos.dj2addons"
version = "1.3.0"
val proj: IdeaProject = idea.project
idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
        inheritOutputDirs = true // Fix resources in IJ-Native runs
    }
}
allprojects {
    
    proj.withGroovyBuilder {
        "settings" {
            "compiler" {
                val self = this.delegate as org.jetbrains.gradle.ext.IdeaCompilerConfiguration
                afterEvaluate {
                    if (self.javac.moduleJavacAdditionalOptions == null) {
                        self.javac.moduleJavacAdditionalOptions = mutableMapOf()
                    }
                    self.javac.moduleJavacAdditionalOptions[(project.name + ".main")] = tasks.compileJava.get()
                                                                                             .options
                                                                                             .compilerArgs
                                                                                             .stream()
                                                                                             .map {'"' + it + '"'}
                                                                                             .collect(Collectors.joining(" "))
                }
            }
        }
    }
    
    
    
}



