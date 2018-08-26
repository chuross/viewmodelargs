package com.github.chuross.viewmodelargs

import androidx.lifecycle.ViewModel
import com.github.chuross.viewmodelargs.annotation.Argument
import com.github.chuross.viewmodelargs.annotation.ViewModelWithArgs

@ViewModelWithArgs
class MainViewModel : ViewModel() {

    @Argument
    lateinit var hoge: String

    @Argument(required = false)
    var fuga: Int = 0

}