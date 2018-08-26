package com.github.chuross.viewmodelargs.compiler

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName

object Parameters {

    fun nullable(typeName: TypeName, methodName: String): ParameterSpec {
        return ParameterSpec.builder(typeName, methodName)
                .addAnnotation(Nullable::class.java)
                .build()
    }

    fun nonNull(typeName: TypeName, methodName: String): ParameterSpec {
        return ParameterSpec.builder(typeName, methodName)
                .addAnnotation(NonNull::class.java)
                .build()
    }

}