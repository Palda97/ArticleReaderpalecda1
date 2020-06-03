# ArticleReader by palecda1

## Téma

Aplikace Article Reader je čtečka RSS feedů. Dokáže sama periodicky stahovat články ze zadaných RSS feedů, ukládat je lokálně do SQLite databáze a upozorní uživatele notifikací na nové články.

## Architektura

![architecture](https://raw.githubusercontent.com/Palda97/ArticleReaderpalecda1/master/pics/articleReader.png)

Aplikace používá architekturu **MVVM** (Model-View-ViewModel)

Následující vrstvy používají tyto technologie:

- View
  - [Data Binding](https://developer.android.com/topic/libraries/data-binding)
- ViewModel

- Repository
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
  - [Rome](http://rometools.github.io/rome/) ([android verze](https://code.google.com/archive/p/android-rome-feed-reader/))
  - [Room](https://developer.android.com/training/data-storage/room)

## Stručný popis použitých knihoven

- Data Binding
  - Knihovna pro lepší provázání XML layoutu s daty pro prezentaci.
- LiveData
  - Knihovna umožňující sledování dat. Je provázána s životním cyklem activit a fragmentů a tím šetří cpu time a snaží se vyvarovat memory leakům.
- Rome
  - Knihovna pro stahování a parsování XML feedů.
- Room
  - Knihovna pro vytváření DAO pro SQLite

## Kompilace a spuštění

Kompilovat v [Android Studiu](https://developer.android.com/studio/). Aplikace by měla běžet na všech mobilních zařízení s verzí androidu 5.0 a vyšší.

## Příklad použití

Uživatel otevře aplikaci. Při prvním otevření se automaticky nastaví 2 předdefinované zdroje pro RSS feedy. Uživatel může kliknout na refresh ikonku vpravo nahoře v action baru, nebo může kliknout na ikonu RSS a otevře se mu nastavení RSS feedů. Zde si může nastavit, z jakých zdrojů se budou stahovat články. Šipkou zpět se opět dostane na seznam článků. Při zobrazení na výšku se po vybrání článku otevře nová obrazovka s vybraným článkem. Při zobrazení na šířku se po vybrání článku článek otevře na stejné obrazovce vlevo. Při prohlížení článku lze pomocí ikonu globusu přejít na webovou stránku s článkem, nebo pomocí ikony pro sdílení sdílet url článku.

## Snímky obrazovky

![screanshot 1](https://raw.githubusercontent.com/Palda97/ArticleReaderpalecda1/master/pics/scre/scr1.jpeg)
![screanshot 2](https://raw.githubusercontent.com/Palda97/ArticleReaderpalecda1/master/pics/scre/scr2.jpeg)
![screanshot 3](https://raw.githubusercontent.com/Palda97/ArticleReaderpalecda1/master/pics/scre/scr3.jpeg)
![screanshot 4](https://raw.githubusercontent.com/Palda97/ArticleReaderpalecda1/master/pics/scre/scr4.jpeg)
