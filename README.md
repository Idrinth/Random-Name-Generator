# Random Name Generator

[![Build Status](https://travis-ci.org/Idrinth/Random-Name-Generator.svg?branch=master)](https://travis-ci.org/Idrinth/Random-Name-Generator)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/9204972bf0d343f099b352d75b196062)](https://www.codacy.com/app/Idrinth/Random-Name-Generator?utm_source=github.com&utm_medium=referral&utm_content=Idrinth/Random-Name-Generator&utm_campaign=Badge_Coverage)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9204972bf0d343f099b352d75b196062)](https://www.codacy.com/app/Idrinth/Random-Name-Generator?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Idrinth/Random-Name-Generator&amp;utm_campaign=Badge_Grade)
[![Release](https://jitpack.io/v/de.idrinth/random-name-generator.svg)](https://jitpack.io/#de.idrinth/random-name-generator)

This is both a library for generating names as well as one for collecting data about names.

## Name Generation

Access the API-Class to generate names based on the default data. Length and Letter positioning are based on the data previously collected.
Further names can be supplied as a list or one by one during runtime.
The results are based on the length distribution as well as the distribution of character groups in the sources used. Longer strings of known characters are valued higher than shorter strings.

## Data Collection

While not intened for production use, it is possible to parse your own name lists, generating more data about letter combinations and their rarity. Have a look at the main Method of the API class for that.

## Future Plans

Further expanding of the name generation by allowing multiple locations to be chosen between randomly.

## Maven inclusion

Add the repository:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

And the actual dependency:

```xml
<dependency>
    <groupId>de.idrinth</groupId>
    <artifactId>random-name-generator</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Output Examples

For version 1.0.1

```
Minal
Wwna
Cconst
Glene
Cfredd
Lziers
Andre
Iahjsa
Ernad
Hett
Tjrker
Chain
Talmar
Dyso
Pagene
Vmelv
Entiny
Lerm
Shane
Gessi
Rman
Fyla
Ciann
Bella
Bdyl
Aeland
Twro
Wtoni
Bmsva
Aaron
Monty
Ctavi
Zierr
Gcere
Manue
Nyasm
Sondr
Tteodo
Lisend
Ja
Anell
Btney
Books
Wjdari
Eyan
Jlah
Step
Rhett
Icarl
Rlandr
Alupe
Jlrd
Deja
Dtbra
Vtwhitn
Rick
Jsje
Jlenora
Mhetta
Jacoby
Rsonn
Cesara
Britne
Rienny
Manu
Liano
Celene
Hallis
Cent
Ardon
Ckyles
Dtgda
Mgibso
Dylan
Weldo
Aniba
Reval
Naldo
Danni
Ierald
Awand
Saynar
Bria
Piers
Jeanor
Prriet
Cat
Jndian
Aakotahj
Lerite
Sonya
Fcosmo
Blaid
Kitan
Kaylor
Wayna
Oinez
Ldahlo
Henels
Enator
```
