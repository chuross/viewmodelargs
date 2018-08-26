package com.github.chuross.viewmodelargs.compiler.processors

import com.github.chuross.viewmodelargs.compiler.ProcessorContext
import com.github.chuross.viewmodelargs.compiler.extention.isViewModelArgs
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier

object BuilderProcessor {

    fun getGeneratedTypeName(element: Element): String {
        return "${element.simpleName}Builder"
    }

    fun process(element: Element) {
        if (!element.isViewModelArgs) return

        val typeSpec = TypeSpec.classBuilder(getGeneratedTypeName(element)).also { builder ->
            builder.addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            builder.addJavadoc("This class is auto generated.")
        }.build()

        val context = ProcessorContext.getInstance()

        JavaFile.builder(context.elementUtils.getPackageOf(element).qualifiedName.toString(), typeSpec)
                .build()
                .writeTo(context.filer)
    }
}