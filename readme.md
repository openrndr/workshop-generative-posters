# Generative & Data Driven Posters

This is the code that accompanies the workshop

## Setup

 - Follow the [requirements](https://guide.openrndr.org/#/Tutorial_Start?id=requirements) and [prerequisites](https://guide.openrndr.org/#/Tutorial_Start?id=setting-up-prerequisites()) sections in the [OpenRNDR guide](https://guide.openrndr.org/).
 - Clone this repository and open it in IntelliJ.<br>
   *Hint*: You can do this from within IntelliJ if you're unfamiliar with [git](https://git-scm.com/).<br>
   Do the following:
   - Open IntelliJ
   - In the menubar, navigate: VCS / Checkout from Version Control / Git<br>
     Use URL: https://github.com/openrndr/workshop-generative-posters


## Project Structure
This project has three modules:

1. **toolkit**<br>
This module contains the tools that you'll be using during the workshop.

2. **examples**<br>
This module contains examples of how you can use the `toolkit`.

3. **workshop**<br>
This is an empty directory structure reserved for your own work.

## Getting Started
To get started, copy some file from `examples/src/main/kotlin/` to `workshop/src/main/kotlin` and start tweaking it.


## Using the toolkit in a standalone project
If you'd like to use the toolkit in your own project, you can do the following:
- Clone the [OpenRNDR template project](https://github.com/openrndr/openrndr-gradle-template)
- Add the following to the project dependencies in `build.gradle`:<br>
```compile "com.github.openrndr.workshop-generative-posters:toolkit:-SNAPSHOT"```
- Replace `TemplateProgram.kt` with something from the `examples` module in this repository and run it.

## Example overview
TODO

 <!--- effects-001 - examples based on StepWave effects-->
 <!--- effects-002 - examples based on ZoomMosaic effect-->
 <!--- effects-003 - examples based on Separate effect-->
 <!--- folder-monitor-001 - example that generates posters from images and text files placed in a folder-->
 <!--- images-001 - example in which posters are drawn from images that dragged onto the program window-->
 <!--- rss-001 - example that draws posters from an RSS feed-->
 <!--- shapes-001 - example based on multiply blended circles-->
 <!--- shapes-002 - example based on multiply blended lines-->
 <!--- shapes-003 - example based on multiply blended bezier curves-->
 <!--- textfile-001 - example that draws a poster from a text file-->
 <!--- typography-001 - example based on typographic layers with post processing-->
 <!--- typography-002 - example based on typographic layers with post processing-->
 <!--- video-001 - example that that creates rudimentary posters from a video that is dragged on the program window-->
 <!--- website-001 - example that that creates rudimentary posters from a video that is dragged on the program window-->

<!--The folders without number contain support modules and do not contain runnable software-->

 <!--- filters - a collection of shader based filters-->
 <!--- poster - the poster/layer DSL framework-->
 <!--- rss - a simple RSS parser-->
