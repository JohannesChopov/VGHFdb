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
Musea dienen om game kopies in tentoon te stellen.
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
* titel : Games hebben titels. Het datatype hiervan is String.
* platformID : Een spel heeft ook een console waar die aan gelinkt is. Dat gebeurt met de platformID. Dit is de id van de entity Platform. Het datatype is een int.
* genre : het genre van het spel kan ook aangeduid worden om deze zo makkelijker terug te kunnen vinden.

### 2.6 Gamecopy
Er zijn verschillende spellen van een titel, want een titel wordt onder meerdere kopies verkocht. Zo kunnen er bijvoorbeeld 10 kopies van hetzelfde spel tentoongesteld worden in een museum en 5 andere kopies opgeslagen zijn in een warenhuis.
* id : Elke kopie heeft zijn eigen unieke id waarmee deze onderscheiden wordt van andere kopies van hetzelfde spel.
* gameID : Van welk spel is het een kopie? Zo is de entity Gamecopy verbonden met Game.
* museumID : In welk museum is de kopie tentoongesteld? Als dit veld is ingevuld voor de kopie weten we dat deze in een museum is. Deze is exclusief met de warenhuisID die hieronder is uitgelegd.
* warenhuisID : In welk warenhuis is de kopie opgeslagen? Als dit veld is ingevuld betekent dat dat de kopie in kwestie niet is tentoongesteld in een museum, maar is opgesteld in een warenhuis.
De twee laatste velden kunnen dus niet tegelijkertijd een waarde hebben ingevuld.

### 2.7 Warenhuis
Een warenhuis is een opslagplaats voor kopies van een game. 
* id : Een warenhuis heeft zijn eigen unieke id waarmee deze geïdentificeerd wordt.
* naam : Het warenhuis heeft ook een naam. De naam van het gebouw heeft een type String.
* adres : Het warenhuis heeft een adres waardoor we weten hoe we daar kunnen geraken. Dit is van het type String.

## 3 Relaties
Hieronder worden de relaties tussen verschillende entiteiten beschreven en uitgelegd.
### 3.1 Donatie - Museum
Een museum kan meerdere donaties hebben, maar een donatie kan alleen maar aan één museum gegeven worden. Een museum kan ook 0 donaties ontvangen. De relatie donatie - museum is een één op veel relatie.
### 3.2 Bezoeker - Museum
Een bezoeker is pas een bezoeker als die een museum bezocht heeft. Een bezoeker kan ook meerdere keren een museum hebben bezocht en andere museums bezoeken. Een museum kan 0 of meer bezoekers hebben. Zo blijkt de relatie bezoeker - museum een veel op veel relatie te zijn. Voor deze veel op veel relatie zal er ook een koppeltabel nodig zijn waarmee er makkelijk achterhaald kan worden welke musea een bezoeker heeft bezocht en hoeveel bezoekers een museum heeft gehad etc. De koppeltabel zou bezoekerMuseum noemen waarin een id, een bezoekerID en een museumID bestaan. Voor deze opdracht is die voor de duidelijkheid nog niet gemaakt. De bezoekerID in deze tussentabel komt van de id in Bezoeker en de museumID komt van de id in Museum.
### 3.3 Museum - Gamecopy
Gamekopieën kunnen worden tentoongesteld in musea. Een museum is gedefinieerd in onze database onder het feit dat deze minstens één gamecopy moet hebben tentoongesteld. Anders is het geen museum. Zo stellen we dat een museum één of meer gamekopieën bevat en een gamecopy in maar één museum kan zijn tentoongesteld, op één tijdsstip natuurlijk. De relatie museum - gamecopy is een één op veel relatie.
### 3.4 Warenhuis - Gamecopy
Gamekopieën kunnen worden opgeslagen in warenhuizen. Een warenhuis is gedefinieerd in onze database onder het feit dat deze één kopie of meer opgeslagen kan hebben. Zo stellen we dat een warenhuis nul of meer gamekopieën bevat en een gamecopy in maar één warenhuis kan zijn opgeslagen, op één tijdsstip natuurlijk. De relatie warenhuis - gamecopy is een één op veel relatie.
### 3.5 Gamecopy - Game
De entity Gamecopy is gemaakt om een kopie voor te stellen van een game. Zo is het vanzelfsprekend dat een game één of meer gamekopieën kan hebben. Dit is wel nog te overwegen want een gametitel zou ook alle gamekopieën vernietigd kunnen hebben, waardoor er van een game 0 kopieën zouden kunnen bestaan. Dan heeft het wel geen nut om die titel van het spel nog in het database te houden, want dan kan er effectief niks gedaan worden met die game. Elke gamekopie kan wel maar van één gametitel komen. Bijvoorbeeld één DVD van meerdere games hebben we als onmogelijk beschouwd maar dit zou nog kunnen worden aangepast aangezien er in de werkelijkheid wel DVD's bestaan met een reeks van games op. Zo is de relatie gamecopy - game een één op veel relatie.
### 3.6 Game - Platform
Één Gametitel bestaat ook voor verschillende platforms of consoles. Zo kan bijvoorbeeld een game zoals Grand Theft Auto 5 bestaan voor de Playstation 3, Playstation 4, Xbox 360 en Xbox One. De entity Game is dan ook enkel de gametitel van het spel, er wordt niet direct een onderscheid gemaakt voor welk platform de game is gemaakt. Zo kan een Game entity voor meerdere platformen zijn gemaakt als in het voorbeeld van GTA5. Een game kan dus voor één of meer platforms zijn gemaakt. Niet nul, want dan is er geen nut voor de game. In de database geldt er ook de gedachte dat een platform één of meer gametitels kan hebben. Zo is er tussen de twee een veel op veel relatie. Hoe deze in werkelijkheid met elkaar gekoppeld gaan worden is met een koppeltabel tussenin genaamd GamePlatform. Hierin gaat er een id van GamePlatform, een platformID en een gameID bestaan waarmee er makkelijk achterhaald kan worden wat de games zijn van een bepaalde console etc.
