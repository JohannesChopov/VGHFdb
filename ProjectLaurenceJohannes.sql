BEGIN TRANSACTION;
DROP TABLE IF EXISTS "GamePlatform";
CREATE TABLE IF NOT EXISTS "GamePlatform" (
	"gameplatformID"	INTEGER NOT NULL UNIQUE,
	"gameID"	INTEGER NOT NULL,
	"platformID"	INTEGER NOT NULL,
	PRIMARY KEY("gameplatformID" AUTOINCREMENT),
	FOREIGN KEY("platformID") REFERENCES "Platform"("platformID") ON DELETE CASCADE,
	FOREIGN KEY("gameID") REFERENCES "Game"("gameID") ON DELETE CASCADE
);
DROP TABLE IF EXISTS "Game";
CREATE TABLE IF NOT EXISTS "Game" (
	"gameID"	INTEGER NOT NULL UNIQUE,
	"titel"	TEXT NOT NULL UNIQUE,
	"genre"	TEXT NOT NULL,
	PRIMARY KEY("gameID" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "Platform";
CREATE TABLE IF NOT EXISTS "Platform" (
	"platformID"	INTEGER NOT NULL UNIQUE,
	"naam"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("platformID" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "Warenhuis";
CREATE TABLE IF NOT EXISTS "Warenhuis" (
	"warenhuisID"	INTEGER NOT NULL UNIQUE,
	"naam"	TEXT NOT NULL UNIQUE,
	"adres"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("warenhuisID" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "GameCopy";
CREATE TABLE IF NOT EXISTS "GameCopy" (
	"gamecopyID"	INTEGER NOT NULL UNIQUE,
	"gameplatformID"	INTEGER NOT NULL,
	"museumID"	INTEGER,
	"warenhuisID"	INTEGER,
	CHECK(("museumID" IS NOT NULL AND "warenhuisID" IS NULL) OR ("museumID" IS NULL AND "warenhuisID" IS NOT NULL)),
	PRIMARY KEY("gamecopyID" AUTOINCREMENT),
	FOREIGN KEY("warenhuisID") REFERENCES "Warenhuis"("warenhuisID") ON DELETE CASCADE,
	FOREIGN KEY("museumID") REFERENCES "Museum"("museumID") ON DELETE CASCADE,
	FOREIGN KEY("gameplatformID") REFERENCES "GamePlatform"("gameplatformID") ON DELETE CASCADE
);
DROP TABLE IF EXISTS "Museum";
CREATE TABLE IF NOT EXISTS "Museum" (
	"museumID"	INTEGER NOT NULL UNIQUE,
	"naam"	TEXT NOT NULL UNIQUE,
	"inkomprijs"	REAL NOT NULL CHECK("inkomprijs" >= 0.0),
	"adres"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("museumID" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "Donatie";
CREATE TABLE IF NOT EXISTS "Donatie" (
	"donatieID"	INTEGER NOT NULL UNIQUE,
	"som"	REAL NOT NULL CHECK("som" > 0.0),
	"museumID"	INTEGER NOT NULL,
	"datum"	DATE NOT NULL,
	PRIMARY KEY("donatieID" AUTOINCREMENT),
	FOREIGN KEY("museumID") REFERENCES "Museum"("museumID")
);
DROP TABLE IF EXISTS "Bezoeker";
CREATE TABLE IF NOT EXISTS "Bezoeker" (
	"bezoekerID"	INTEGER NOT NULL UNIQUE,
	"naam"	TEXT NOT NULL CHECK(LENGTH("naam") <= 50 AND "naam" NOT LIKE '%[0-9]%') UNIQUE,
	PRIMARY KEY("bezoekerID" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "User";
CREATE TABLE IF NOT EXISTS "User" (
	"userID"	INTEGER NOT NULL UNIQUE,
	"username"	TEXT NOT NULL UNIQUE,
	"password"	TEXT NOT NULL,
	PRIMARY KEY("userID" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "MuseumBezoek";
CREATE TABLE IF NOT EXISTS "MuseumBezoek" (
	"museumbezoekID"	INTEGER NOT NULL UNIQUE,
	"museumID"	INTEGER NOT NULL,
	"bezoekerID"	INTEGER NOT NULL,
	"datum"	DATE NOT NULL,
	PRIMARY KEY("museumbezoekID" AUTOINCREMENT),
	FOREIGN KEY("museumID") REFERENCES "Museum"("museumID") ON DELETE CASCADE,
	FOREIGN KEY("bezoekerID") REFERENCES "Bezoeker"("bezoekerID") ON DELETE CASCADE
);
INSERT INTO "GamePlatform" ("gameplatformID","gameID","platformID") VALUES (1,1,1),
 (2,1,4),
 (3,2,1),
 (4,2,2),
 (5,2,4),
 (6,3,3),
 (7,4,1),
 (8,4,2),
 (9,4,4),
 (10,5,1),
 (11,5,2),
 (12,5,4),
 (13,5,5),
 (14,6,3),
 (15,7,1),
 (16,7,2),
 (17,7,4),
 (18,7,5),
 (19,8,4),
 (20,8,5),
 (21,9,4),
 (22,10,1),
 (23,10,2),
 (24,10,4);
INSERT INTO "Game" ("gameID","titel","genre") VALUES (1,'The Witcher 3: Wild Hunt','Action RPG'),
 (2,'FIFA 22','Sports'),
 (3,'Super Mario Odyssey','Platformer'),
 (4,'Call of Duty: Warzone','First-person Shooter'),
 (5,'Minecraft','Sandbox'),
 (6,'The Legend of Zelda: Breath of the Wild','Action-Adventure'),
 (7,'Fortnite','Battle Royale'),
 (8,'Among Us','Social Deduction'),
 (9,'League of Legends','MOBA'),
 (10,'Assassins Creed Valhalla','Action RPG');
INSERT INTO "Platform" ("platformID","naam") VALUES (1,'PlayStation'),
 (2,'Xbox'),
 (3,'Nintendo Switch'),
 (4,'PC'),
 (5,'Mobile'),
 (6,'VR'),
 (7,'Mac'),
 (8,'Linux'),
 (9,'Android'),
 (10,'iOS');
INSERT INTO "Warenhuis" ("warenhuisID","naam","adres") VALUES (1,'warenhuis1','Molenweg 195, Zonhoven'),
 (2,'warenhuis2','Steenweg 60, Bree'),
 (3,'warenhuis3','Kopstraat 40, Hasselt'),
 (4,'warenhuis4','Schoolstraat 10, Heusden'),
 (5,'warenhuis5','Industrielaan 800, Beringen');
INSERT INTO "GameCopy" ("gamecopyID","gameplatformID","museumID","warenhuisID") VALUES (1,1,NULL,1),
 (2,2,NULL,2),
 (3,3,NULL,3),
 (4,4,NULL,4),
 (5,5,NULL,5),
 (6,6,1,NULL),
 (7,7,2,NULL),
 (8,8,3,NULL),
 (9,9,4,NULL),
 (10,10,5,NULL),
 (11,1,NULL,2),
 (12,1,NULL,3),
 (13,6,2,NULL),
 (14,6,3,NULL),
 (15,2,NULL,1),
 (16,2,NULL,3),
 (17,2,NULL,4),
 (18,7,1,NULL),
 (19,7,3,NULL),
 (20,7,4,NULL),
 (21,11,NULL,1),
 (22,11,NULL,2),
 (23,12,1,NULL),
 (24,12,3,NULL);
INSERT INTO "Museum" ("museumID","naam","inkomprijs","adres") VALUES (1,'GamesCom',15.99,'Vennestraat 80, Genk'),
 (2,'FACTS',12.5,'Steenweg 50, Diepenbeek'),
 (3,'SpelletjesMuseum',18.75,'Veugenstraat 48, Zutendaal'),
 (4,'PACKS',20.5,'Begijnhof 22, Bilzen'),
 (5,'Pixel',14.25,'Kattenbos 67, Lommel');
INSERT INTO "Donatie" ("donatieID","som","museumID","datum") VALUES (1,100.0,1,'2023-01-15'),
 (2,50.0,2,'2023-02-20'),
 (3,200.0,3,'2023-03-10'),
 (4,75.0,4,'2023-04-05'),
 (5,150.0,5,'2023-05-12');
INSERT INTO "Bezoeker" ("bezoekerID","naam") VALUES (1,'Bob'),
 (2,'Tom'),
 (3,'Jonas'),
 (4,'Bart'),
 (5,'Gert');
INSERT INTO "User" ("userID","username","password") VALUES (1,'user1','password1'),
 (2,'user2','password2'),
 (3,'user3','password3'),
 (4,'user4','password4'),
 (5,'user5','password5');
INSERT INTO "MuseumBezoek" ("museumbezoekID","museumID","bezoekerID","datum") VALUES (1,1,1,'2024-01-17'),
 (2,2,2,'2024-01-18'),
 (3,3,3,'2024-01-19'),
 (4,4,4,'2024-01-20'),
 (5,5,5,'2024-01-21'),
 (6,1,2,'2024-01-22'),
 (7,2,3,'2024-01-23'),
 (8,3,4,'2024-01-24'),
 (9,4,5,'2024-01-25'),
 (10,5,1,'2024-01-26'),
 (11,1,3,'2024-01-27'),
 (12,2,4,'2024-01-28'),
 (13,3,5,'2024-01-29'),
 (14,4,1,'2024-01-30'),
 (15,5,2,'2024-01-31');
COMMIT;
