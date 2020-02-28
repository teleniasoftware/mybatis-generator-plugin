package com.telenia.gradle

import org.gradle.api.file.FileCollection
import org.gradle.api.internal.ConventionTask
import org.gradle.api.internal.project.IsolatedAntBuilder
import org.gradle.api.tasks.TaskAction

/**
 * Created by Maomao Chen on 2/4/16.
 */
class MybatisGeneratorTask extends ConventionTask {

    MybatisGeneratorTask(){
        description = 'Mybatis Generator Task'
        group = 'Util'
    }

    def overwrite
    def configFile
    def verbose
    def targetDir
    def mybatisProperties
    FileCollection mybatisGeneratorClasspath

    @TaskAction
    void executeCargoAction() {
        services.get(IsolatedAntBuilder).withClasspath(getMybatisGeneratorClasspath()).execute {
            ant.taskdef(name: 'mbgenerator', classname: 'org.mybatis.generator.ant.GeneratorAntTask')

            ant.properties['generated.source.dir'] = getTargetDir()
            getMybatisProperties().each{key, val ->
                ant.properties[key] = val
            }
            ant.mbgenerator(overwrite: getOverwrite(), configfile: getConfigFile(), verbose: getVerbose())
        }
    }

}
