package com.github.chuross.viewmodelargs.compiler.extention

import com.github.chuross.viewmodelargs.annotation.Argument
import com.github.chuross.viewmodelargs.annotation.ViewModelWithArgs
import javax.lang.model.element.Element

val Element.isViewModelArgs: Boolean get() = getAnnotation(ViewModelWithArgs::class.java) != null

val Element.isArgument: Boolean get() = getAnnotation(Argument::class.java) != null

val Element.isRequiredArgument: Boolean get() = getAnnotation(Argument::class.java)?.required ?: false

val Element.argumentElements: List<Element> get() = enclosedElements.filter { it.getAnnotation(Argument::class.java) != null }

val Element.argumentName: String?
    get() {
        val annotation = getAnnotation(Argument::class.java) ?: return null
        return annotation.name.takeIf { it.isNotBlank() } ?: simpleName.toString()
    }