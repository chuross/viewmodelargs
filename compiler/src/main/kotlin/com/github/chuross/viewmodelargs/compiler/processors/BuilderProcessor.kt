package com.github.chuross.viewmodelargs.compiler.processors

import com.github.chuross.viewmodelargs.compiler.PackageNames
import com.github.chuross.viewmodelargs.compiler.Parameters
import com.github.chuross.viewmodelargs.compiler.ProcessorContext
import com.github.chuross.viewmodelargs.compiler.extention.*
import com.squareup.javapoet.*
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
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
            builder.addFields(argumentFields(element))
            builder.addMethod(constructorMethod(element))
            builder.addMethods(optionalParameterMethods(element))
            builder.addMethods(buildMethods(element))
        }.build()

        val context = ProcessorContext.getInstance()

        JavaFile.builder(context.elementUtils.getPackageOf(element).qualifiedName.toString(), typeSpec)
                .build()
                .writeTo(context.filer)
    }

    private fun argumentFields(element: Element): Iterable<FieldSpec> {
        return element.argumentElements.map {
            FieldSpec.builder(TypeName.get(it.asType()), it.argumentName!!.normalize())
                    .addModifiers(Modifier.PRIVATE)
                    .build()
        }
    }

    private fun constructorMethod(element: Element): MethodSpec {
        ProcessorContext.log(element.javaClass.enclosingConstructor)

        val requiredRouterParamElements = element.argumentElements.filter { it.isRequiredArgument }

        return MethodSpec.constructorBuilder().also { builder ->
            builder.addModifiers(Modifier.PUBLIC)
            requiredRouterParamElements.forEach {
                val name = it.argumentName?.normalize() ?: return@forEach
                builder.addParameter(Parameters.nonNull(TypeName.get(it.asType()), name))
                builder.addStatement("this.$name = $name")
            }
        }.build()
    }

    private fun optionalParameterMethods(element: Element): Iterable<MethodSpec> {
        return element.argumentElements
                .filter { !it.isRequiredArgument }
                .map {
                    val name = it.argumentName!!.normalize()

                    MethodSpec.methodBuilder(name)
                            .addModifiers(Modifier.PUBLIC)
                            .addParameter(Parameters.nullable(TypeName.get(it.asType()), name))
                            .addStatement("this.$name = $name")
                            .addStatement("return this")
                            .returns(ClassName.bestGuess(getGeneratedTypeName(element)))
                            .build()
                }

    }

    private fun buildMethods(element: Element): Iterable<MethodSpec> {
        val buildMethodSet = listOf(
                "activity" to PackageNames.APP_COMPAT_ACTIVITY,
                "fragment" to PackageNames.FRAGMENT
        )

        return buildMethodSet.map { buildMethod ->
            MethodSpec.methodBuilder("build").also { builder ->
                val viewModelClassName = ClassName.get(element.asType())

                builder.addModifiers(Modifier.PUBLIC)
                builder.addParameter(ClassName.bestGuess(buildMethod.second), buildMethod.first)
                builder.returns(viewModelClassName)

                val codeBlock = CodeBlock.builder().also { builder ->
                    builder.beginControlFlow("new ${PackageNames.VIEW_MODEL_PROVIDER}.Factory()")
                    builder.indent()
                    builder.add("@Override\n")
                    builder.beginControlFlow("public <T extends ${PackageNames.VIEW_MODEL}> T create(Class<T> modelClass)")
                    builder.indent()
                    builder.addStatement("$viewModelClassName viewModel = new $viewModelClassName()")
                    element.argumentElements.forEach {
                        val name = it.argumentName!!.normalize()
                        val setterMethodName = "set${it.simpleName.toString().capitalize()}"
                        val setterMethod = element.enclosedElements.find {
                            it.kind == ElementKind.METHOD && it.simpleName.toString() == setterMethodName
                        }
                        if (setterMethod == null) {
                            builder.addStatement("viewModel.${it.simpleName} = $name")
                        } else {
                            builder.addStatement("viewModel.$setterMethodName($name)")
                        }
                    }
                    builder.addStatement("return (T) viewModel")
                    builder.endControlFlow()
                    builder.endControlFlow()
                }.build()

                builder.addStatement("return ${PackageNames.VIEW_MODEL_PROVIDERS}.of(${buildMethod.first}, $codeBlock).get($viewModelClassName.class)")
            }.build()
        }
    }
}