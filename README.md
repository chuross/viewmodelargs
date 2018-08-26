# ViewModelArgs
[![](https://jitpack.io/v/chuross/viewmodelargs.svg)](https://jitpack.io/#chuross/viewmodelargs)


Annotation Processor to create for Android ViewModel.

This Library provide easy create `androidx.lifecycle.ViewModel` without implement `ViewModelProvider.Factory`.

This library heavy inspired by [FragmentArgs](https://github.com/sockeqwe/fragmentargs).

## Futures
- Auto generate ViewModelBuilder codes

## Download
1. add JitPack repository to your project root build.gradle.

```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```

2. add the dependency 

[![](https://jitpack.io/v/chuross/viewmodelargs.svg)](https://jitpack.io/#chuross/viewmodelargs)


```gradle
dependencies {
    implementation 'com.github.chuross.viewmodelargs:annotation:x.x.x'
    annotationProcessor 'com.github.chuross.viewmodelargs:compiler:x.x.x' // or kpt
}
```

## Usage
add `@ViewModelWithArgs` and `@Argument`(optional) to your ViewModel class.

```kotlin
@ViewModelWithArgs
class MainViewModel : ViewModel() {

    @Argument
    lateinit var hoge: String

    @Argument(required = false)
    var fuga: Int = 0

}
```

use auto generated builder class.

`MainViewModelBuilder` class is auto generated.

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ....

        val viewModel = MainViewModelBuilder("fooo").fuga(9999).build(this)
        // build(this):
        //   androidx.appcompat.app.AppCompatActivity
        //     or androidx.fragment.app.Fragment

        Toast.makeText(this, "hoge: ${viewModel.hoge} fuga: ${viewModel.fuga}", Toast.LENGTH_SHORT).show() // hoge: fooo, fuga: 9999
    }
}
```