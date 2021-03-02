# Adoption App

<!--- Replace <OWNER> with your Github Username and <REPOSITORY> with the name of your repository. -->
<!--- You can find both of these in the url bar when you open your repository in github. -->
![Workflow result](https://github.com/myotive/android-compose-dev-challenge/workflows/Check/badge.svg)


## :scroll: Description
<!--- Describe your app in one or two sentences -->
Contained in this repository is my first Android app using Jetpack Compose. This developer challenge was to build a pet adoption application with a list of animals and a corresponding detail screen.


## :bulb: Motivation and Context
<!--- Optionally point readers to interesting parts of your submission. -->
My motivation was to finally sit down and learn Jetpack Compose. I am an Android developer by trade and a Flutter developer by interest, so I wanted to see how the two platforms compare. I'm really glad to see Android updating the UI stack and head in a more preformat direction. I think it's fair to compare the two platforms together and I think Android is making huge strides in closing the gap.

<!--- What are you especially proud of? -->
While my UI isn't much of a spectacle, I was really impressed by how quickly I could produce a list/detail application. A couple notable things:

### Navigation
At first, I didn't quite understand how to navigate between screens in compose, so I decided to take a look at the state of the Navigation components. While this library did work for me for this solution, I realized at the end (when I learned about managing state in Compose), I could have just done the UI using a more reactive pattern (like MVI).

However, I did like that I had the choice of using Navigation along side compose and I think that will come in handy in the future should I need to develop an application that hands deep links.

### Database
I went above and beyond and decided to implement a persistent store for all of my animals. I wanted to have both cats and dogs in my adoption app, so I created the models, randomized the data, and persisted in the backend so I could utilize livedata and UI models. I think Jetpack Composes' stateful approach to architecture is super interesting and I look forward to digging into that in the future.


## :camera_flash: Screenshots
<!-- You can add more screenshots here if you like -->
<img src="/results/screenshot_1.png" width="260">&emsp;<img src="/results/screenshot_2.png" width="260">

## License
```
Copyright 2020 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```