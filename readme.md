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
To get started, copy some file from `examples/src/main/kotlin/` to `workshop/src/main/kotlin` and start tweaking it.<br>
<span style="font-size: 15px">
[Also, checkout these examples](examples/README.md)
</span>


## Using the toolkit in a standalone project
If you'd like to use the toolkit in your own project, you can do the following:
- Clone the [OpenRNDR template project](https://github.com/openrndr/openrndr-gradle-template)
- Add the following to the project dependencies in `build.gradle`:<br>
```compile "com.github.openrndr.workshop-generative-posters:toolkit:-SNAPSHOT"```
- Replace `TemplateProgram.kt` with something from the `examples` module in this repository and run it.


## DOCS
### Fonts
TODO Document fonts
<!-- - [0xA000](http://pippin.gimp.org/0xA000/) -->
<!-- - DONE [3270font](https://github.com/rbanffy/3270font) -->
<!-- - [Anonymous Pro](https://www.marksimonson.com/fonts/view/anonymous-pro) -->
<!-- - DONE [Astloch](https://www.fontsc.com/font/astloch) -->
<!-- - DONE [Autopia](http://velvetyne.fr/) -->
<!-- - [Charis SIL](http://software.sil.org/charis/download/) -->
<!-- - [Cormorant](https://github.com/CatharsisFonts/Cormorant) -->
<!-- - DONE [Hanken](https://fontlibrary.org/en/font/hanken) -->
<!-- - DONE [Iosevka](https://github.com/be5invis/Iosevka) -->
<!-- - DONE [Inknut Antiqua](https://github.com/clauseggers/Inknut-Antiqua) -->
<!-- - DONE [Jost](http://indestructibletype.com/Jost.html) -->
<!-- - DONE [Meyrin](https://github.com/optional-is/Meyrin) -->
<!-- - [Now Alt](https://fontlibrary.org/en/font/now-alt) -->
<!-- - [NooAlf fonts](http://www.nooalf.com/index.html) -->
<!-- - [Old Standard](https://www.fontsquirrel.com/fonts/Old-Standard-TT) -->
<!-- - [Playfair Display](https://github.com/clauseggers/Playfair-Display) -->
<!-- - [Pecita](http://pecita.eu/) -->
<!-- - DONE [Rumeur](https://github.com/groupeccc/Rumeur) -->
<!-- - DONE [Rubik](https://github.com/googlefonts/rubik) -->
<!-- - DONE [Space Mono](https://fonts.google.com/specimen/Space+Mono?selection.family=Space+Mono) -->
<!-- - DONE [Syne](https://gitlab.com/bonjour-monde/syne-typeface/tree/master) -->
<!-- - [Savate](https://github.com/CollectifWech/Savate) -->
<!-- - [TODO PICK SOME STUFF FROM HERE](http://velvetyne.fr/) -->
<!-- - DONE [Unifraktur](http://unifraktur.sourceforge.net/) -->
