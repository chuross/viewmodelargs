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

## License
```
Copyright 2018 chuross

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```