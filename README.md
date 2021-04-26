# livedata-kit
Provides classes and extensions to solve common challenges with `LiveData`. 

## Usage
### MergerLiveData
- subscribes to one or more `LiveData` of any type and merges their values
- article: [Merge like you need it](www.medium.com) 

### DistinctLiveData
- checks new values with `equals` and emits only different values to observers

### MutableLiveData-ktx
- provide `List` functions directly on a `MutableLiveData<List>`   

## Getting Started
_livedata-kit_ can be received via [jitpack.io](https://jitpack.io): 

```
//project gradle
allprojects {
    repositories {
        ...
        maven(url = "https://jitpack.io") // kotlin
        maven { url 'https://jitpack.io' } // java
    }
}
```
```
//build gradle
dependencies {
        implementation("com.github.DanielKnauf:livedata-kit:VERSION") // kotlin
        implementation 'com.github.DanielKnauf:livedata-kit:VERSION' // java
}
```

## License
```
MIT License

Copyright (c) 2021 Daniel Knauf

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
