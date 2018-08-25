package com.github.chuross.viewmodelargs.compiler

import com.github.chuross.viewmodelargs.annotation.Argument
import com.github.chuross.viewmodelargs.annotation.ViewModelArgs
import com.google.auto.service.AutoService
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types

@AutoService(Processor::class)
class ViewModelArgsProcessor : AbstractProcessor() {

    lateinit var filer: Filer
    lateinit var messager: Messager
    lateinit var elementUtils: Elements
    lateinit var typeUtils: Types

    @Synchronized
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        filer = processingEnv.filer
        messager = processingEnv.messager
        elementUtils = processingEnv.elementUtils
        typeUtils = processingEnv.typeUtils
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return hashSetOf(
                ViewModelArgs::class.java.canonicalName,
                Argument::class.java.canonicalName
        )
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        return try {
            ProcessorContext.setup(filer, messager, elementUtils, typeUtils)

            return true
        } catch (e: Throwable) {
            return false
        }
    }
}