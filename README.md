# VGHFdb

Database project van Laurence en Johannes

![VGHFschema](https://github.com/JohannesChopov/VGHFdb/assets/99961451/bd3b01aa-a2fd-42c4-aa08-f74f420d4a2b)


## 1. Info
Als projectopdracht voor het vak Databases dienen wij een database te ontwerpen voor de management van de Video Games History Foundation. Om te beginnen dient er een Entity Relationships diagram gemaakt te worden.
## 2. Entities
Er zijn 8 entities: Donatie, Bezoeker, Museum, Platform, Game, Gamecopy, Warenhuis en tenslotte de User. Hun properties worden uitgelegd en de keuze daarvan wordt duidelijker gemaakt in hoofdstuk 3, waarin de relaties worden uitgelegd. String in applicatie is TEXT in database. REAL is een double met hoge precisie. Deze kan negatief zijn maar in de applicatie staan we dit nooit toe.
### 2.1 Donatie
Donaties kunnen worden gegeven aan musea. Zo worden de musea van de VGHF gesteund. De properties van donatie zijn de volgende:
* donatieID : Om donaties van elkaar te kunnen onderscheiden wordt er voor elke donatie een id van het type int gegenereerd.
* som : Wat is de waarde van de donatie in EURO. Hier is er gekozen voor het type REAL. Er hoeven niet per se donaties binnen te komen in de vorm van kommagetallen.
* museumID : Id van het museum om te acherhalen aan welk museum de donatie wordt gegeven.
* datum : De datum van wanneer deze donatie is gedaan dient als extra info. Deze is van het type DATE.

### 2.2 Museum
Musea dienen om gamekopieën in tentoon te stellen.
* museumID : Aan elk museum wordt er een id van het type int gegeven. Zo kunnen we musea van elkaar onderscheiden.
* naam : Elk museum heeft een naam met dataype String.
* inkomprijs : Om het museum te mogen bezichtigen dient er een inkomprijs betaald te worden door elke bezoeker. De inkomprijs kan een kommagetal zijn dus we gebruiken het datatype REAL.
* adres : Het adres van het museum is nuttige informatie zodat men weet waar de gamecopies zich bevinden.

### 2.3 Bezoeker
* bezoekerID : Elke bezoeker wordt gekenmerkt met een id van het datatype int.
* naam : Bezoekers (personen) hebben een naam van het datatype String. Bezoekers moeten steeds een unieke naam ingeven. Stel twee personen hebben dezelfde naam dan worden er karakters bijgevoegd.

### 2.4 Platform
* platformID : Elk platform zal een id hebben waarmee deze makkelijk teruggevonden kan worden. Het datatype hiervan is int. Deze zal gebruikt worden door de entity Game.
* naam : De naam van de console met datatype String.

### 2.5 Game
* id : Elke game wordt geïndexeerd met zijn id van datatype int.
* titel : Games hebben titels. Het datatype hiervan is String.
* genre : Het genre van het spel word ook aangeduid ter extra informatie.

### 2.6 Gamecopy
Er zijn verschillende spellen van een titel, want een titel wordt onder meerdere kopies verkocht. Zo kunnen er bijvoorbeeld 10 kopies van hetzelfde spel tentoongesteld worden in een museum en 5 andere kopies opgeslagen zijn in een warenhuis.
* gamecopyID : Elke kopie heeft zijn eigen unieke id waarmee deze onderscheiden wordt van andere kopies van hetzelfde spel.
* gameplatformID : Id van koppeltabel tussen game en platform.
* museumID : In welk museum is de kopie tentoongesteld? Als dit veld is ingevuld voor de kopie weten we dat deze in een museum is. Deze is exclusief met de warenhuisID die hieronder is uitgelegd.
* warenhuisID : In welk warenhuis is de kopie opgeslagen? Als dit veld is ingevuld betekent dat dat de kopie in kwestie niet is tentoongesteld in een museum, maar is opgeslagen in een warenhuis.
De twee laatste velden kunnen dus niet tegelijkertijd een waarde hebben ingevuld. De ene is null en de andere niet.

### 2.7 Warenhuis
Een warenhuis is een opslagplaats voor kopies van een game. 
* warenhuisID : Een warenhuis heeft zijn eigen unieke id waarmee deze geïdentificeerd wordt.
* naam : Het warenhuis heeft ook een naam. De naam van het gebouw heeft een type String.
* adres : Het warenhuis heeft een adres waardoor we weten hoe we daar kunnen geraken. Dit is van het type String.

### 2.8 User
Voor de applicatie maken we ook gebruik van een login waarmee medewerkers zich kunnen inloggen in het systeem.
* userID: User heeft een primaire sleutel, zijn id.
* username: User heeft een gebruikersnaam waarmee die kan inloggen, deze is uniek.
* password: User heeft ook een wachtwoord nodig om in de applicatie te kunnen geraken. Dit wachtwoord is van het type TEXT, dus het mag alles bevatten.

## 3 Relaties
Hieronder worden de relaties tussen verschillende entiteiten beschreven en uitgelegd.
### 3.1 Donatie - Museum
Een museum kan meerdere donaties hebben, maar een donatie kan alleen maar aan één museum gegeven worden. Een museum kan ook 0 donaties ontvangen. De relatie donatie - museum is een één op veel relatie.
### 3.2 Bezoeker - Museum
Een bezoeker is pas een bezoeker als die een museum bezocht heeft. Een bezoeker kan ook meerdere keren een museum hebben bezocht en andere museums bezoeken. Een bezoeker kan 1 of meer musea hebben bezocht. Een museum kan 0 of meer bezoekers hebben. Zo blijkt de relatie bezoeker - museum een veel op veel relatie te zijn. Voor deze veel op veel relatie zal er ook een koppeltabel nodig zijn waarmee er makkelijk achterhaald kan worden welke musea een bezoeker heeft bezocht en hoeveel bezoekers een museum heeft gehad etc. De koppeltabel noemt MuseumBezoek waarin een museumbezoekID, een museumID, bezoekerID en een datum bestaan. De bezoekerID in deze tussentabel komt van de bezoekerID in Bezoeker en de museumID komt van de museumID in Museum. Zo kan er uit de database worden achterhaald of een bezoeker een museum meermaals heeft bezocht of zelfs meerdere musea meermaals heeft bezocht enzoverder. Eigenlijk stelt de tussentabel van deze twee een "bezoek" voor.

![image](https://github.com/JohannesChopov/VGHFdb/assets/99961451/b114ab4b-5b4a-4d8e-8cc4-9d7ca6d52223)

Hierboven stelt de tussentabel MuseumBezoek dat de bezoeker met de bezoekerID van 2 de musea met museumID 1, 2 en 3 heeft bezocht. Diezelfde bezoeker heeft museum 1 in totaal 2 keer bezocht. Museum 1 is door 2 verschillende bezoekers bezocht namelijk bezoekers 1 en 2. Museum 1 heeft in totaal 3 bezoeken.

### 3.3 Museum - Gamecopy
Gamekopieën kunnen worden tentoongesteld in musea. Een museum kan nul of meerdere gamecopies tentoonstellen. Een gamecopy kan in maar één museum zijn tentoongesteld, op één tijdsstip natuurlijk. Want gamecopies zouden we ook kunnen verplaatsen tussen warenhuizen en musea. De relatie museum - gamecopy is een één op veel relatie.
### 3.4 Warenhuis - Gamecopy
Gamekopieën kunnen ook worden opgeslagen in warenhuizen. Een warenhuis is gedefinieerd in onze database onder het feit dat deze nul of meer opgeslagen kan hebben. Zo stellen we dat een warenhuis nul of meer gamekopieën bevat en een gamecopy in maar één warenhuis kan zijn opgeslagen, op één tijdsstip natuurlijk, omdat we gamecopies kunnen verplaatsen. De relatie warenhuis - gamecopy is een één op veel relatie.
### 3.5 Game - Platform
Één Gametitel bestaat ook voor verschillende platforms of consoles. Zo kan bijvoorbeeld een game zoals Grand Theft Auto 5 bestaan voor de Playstation 3, Playstation 4, Xbox 360 en Xbox One. De entity Game is dan ook enkel de gametitel van het spel, er wordt niet direct een onderscheid gemaakt voor welk platform de game is gemaakt. Zo kan een Game entity voor meerdere platformen zijn gemaakt als in het voorbeeld van GTA5. Een game kan dus voor één of meer platforms zijn gemaakt. Niet nul, want dan is er geen nut voor de game. In de database geldt er ook de gedachte dat een platform één of meer gametitels kan hebben. Zo is er tussen de twee een veel op veel relatie. Hoe deze in werkelijkheid met elkaar gekoppeld gaan is, is met een koppeltabel tussenin genaamd GamePlatform. Hierin gaat er een gameplatformID, een platformID en een gameID bestaan waarmee er makkelijk achterhaald kan worden wat de games zijn van een bepaalde console etc.
### 3.6 Gamecopy - GamePlatform
De entity Gamecopy is gemaakt om een kopie voor te stellen van een Game beschikbaar op een Platform (deze laatste is GamePlatform). Zo is het vanzelfsprekend dat een item uit GamePlatform nul of meer gamekopieën kan hebben. We houden de GamePlatform nog in de database zodat we altijd kopieën kunnen toevoegen. Elke Gamecopy kan wel maar van één GamePlatform komen. Zo is de relatie Gamecopy - GamePlatform een één op veel relatie.
