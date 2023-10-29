# VGHFdb

Database project van Laurence en Johannes

![image](https://github.com/JohannesChopov/VGHFdb/assets/99961451/4c596fb4-be11-4c68-84b5-da7d170d9178)

## 1. Info
Als projectopdracht voor het vak Databases dienen wij een database te ontwerpen voor de management van de Video Games History Foundation. Om te beginnen dient er een Entity Relationships diagram gemaakt te worden.
## 2. Entities
Er zijn 7 entities: Donatie, Bezoeker, Museum, Platform, Game, Gamecopy en ten slotte is er het Warenhuis.
### 2.1 Donatie
Donaties kunnen worden gegeven aan musea. Zo worden de musea van de VGHF gesteund. De properties van donatie zijn de volgende:
* id : om donaties van elkaar te kunnen onderscheiden wordt er voor elke donatie een id van het type int gegenereerd.
* som : wat is de waarde van de donatie in EURO. Hier is er gekozen voor het type integer. Er hoeven niet per se donaties binnen te komen in vorm van kommagetallen.
* museumID : id van de museumentity om te acherhalen aan welk museum de donatie wordt gegeven.

### 2.2 Museum
Musea dienen om videospellen in tentoon te stellen.
* id : Aan elk museum wordt er een id van het type int gegeven. Zo kunnen we musea van elkaar onderscheiden.
* naam : Elk museum heeft een naam met dataype String.
* inkomprijs : Om het museum te mogen bezichtigen dient er een inkomrpijs betaald te worden door elke bezoeker. De inkomprijs zal een geheel getal zijn dus we gebruiken het datatype int.

### 2.3 Bezoeker
* id : Elke bezoeker wordt gekenmerkt met een id van het datatype int.
* naam : Bezoekers (personen) hebben een naam van het datatype String.
* museumID : Elk bezoek aan een museum wordt bijgehouden via de property museumID. Deze heeft een datatype int.

### 2.4 Platform
* id : Elk platform zal een id hebben waarmee deze makkelijk teruggevonden kan worden. Datatype hiervan is int. Deze zal gebruikt worden door de entity Game.
* naam : De naam van de console met datatype String.

### 2.5 Game
* id : Elke game wordt geïndexeerd met zijn id van datatype int.
* titel : Videospellen hebben titels. Het datatype hiervan is String.
* platformID : Een spel heeft ook een console waar die aan gelinkt is. Dat gebeurt met de platformID. Dit is de id van de entity Platform. Het datatype is een int.
* genre : het genre van het spel kan ook aangeduid worden om deze zo makkelijker terug te kunnen vinden.

### 2.6 Gamecopy
Er zijn verschillende spellen van een titel, want een titel wordt onder meerdere kopies verkocht. Zo kunnen er bijvoorbeeld 10 kopies van hetzelfde spel tentoongesteld worden in een museum.
* id :
* gameID:
* museumID:
* warenhuisID:

### 2.7 Warenhuis
* id
* naam
* adres
