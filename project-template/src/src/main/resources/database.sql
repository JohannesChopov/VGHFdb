BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Game" (
	"gameID"	INTEGER NOT NULL UNIQUE,
	"titel"	TEXT NOT NULL UNIQUE,
	"genre"	TEXT NOT NULL,
	PRIMARY KEY("gameID" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Platform" (
	"platformID"	INTEGER NOT NULL UNIQUE,
	"naam"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("platformID" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "GamePlatform" (
	"gameplatformID"	INTEGER NOT NULL UNIQUE,
	"gameID"	INTEGER NOT NULL,
	"platformID"	INTEGER NOT NULL,
	FOREIGN KEY("platformID") REFERENCES "Platform"("platformID"),
	FOREIGN KEY("gameID") REFERENCES "Game"("gameID"),
	PRIMARY KEY("gameplatformID" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Warenhuis" (
	"warenhuisID"	INTEGER NOT NULL UNIQUE,
	"naam"	TEXT NOT NULL UNIQUE,
	"adres"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("warenhuisID" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "GameCopy" (
	"gamecopyID"	INTEGER NOT NULL UNIQUE,
	"gameplatformID"	INTEGER NOT NULL,
	"museumID"	INTEGER,
	"warenhuisID"	INTEGER,
	FOREIGN KEY("warenhuisID") REFERENCES "Warenhuis"("warenhuisID"),
	FOREIGN KEY("gameplatformID") REFERENCES "GamePlatform"("gameplatformID"),
	FOREIGN KEY("museumID") REFERENCES "Museum"("museumID"),
	PRIMARY KEY("gamecopyID" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Museum" (
	"museumID"	INTEGER NOT NULL UNIQUE,
	"naam"	TEXT NOT NULL UNIQUE,
	"inkomprijs"	REAL NOT NULL CHECK("inkomprijs" >= 0.0),
	"adres"	TEXT NOT NULL UNIQUE,
	PRIMARY KEY("museumID" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Donatie" (
	"donatieID"	INTEGER NOT NULL UNIQUE,
	"som"	REAL NOT NULL CHECK("som" >= 0.0),
	"museumID"	INTEGER NOT NULL,
	"datum"	DATE NOT NULL,
	FOREIGN KEY("museumID") REFERENCES "Museum"("museumID"),
	PRIMARY KEY("donatieID" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Bezoeker" (
	"bezoekerID"	INTEGER NOT NULL UNIQUE,
	"naam"	TEXT NOT NULL CHECK(LENGTH("naam") <= 50 AND "naam" NOT LIKE '%[0-9]%'),
	"museumID"	INTEGER NOT NULL,
	PRIMARY KEY("bezoekerID" AUTOINCREMENT)
);
INSERT INTO "Game" VALUES (1,'The Witcher 3: Wild Hunt','Action RPG');
INSERT INTO "Game" VALUES (2,'FIFA 22','Sports');
INSERT INTO "Game" VALUES (3,'Super Mario Odyssey','Platformer');
INSERT INTO "Game" VALUES (4,'Call of Duty: Warzone','First-person Shooter');
INSERT INTO "Game" VALUES (5,'Minecraft','Sandbox');
INSERT INTO "Game" VALUES (6,'The Legend of Zelda: Breath of the Wild','Action-Adventure');
INSERT INTO "Game" VALUES (7,'Fortnite','Battle Royale');
INSERT INTO "Game" VALUES (8,'Among Us','Social Deduction');
INSERT INTO "Game" VALUES (9,'League of Legends','MOBA');
INSERT INTO "Game" VALUES (10,'Assassins Creed Valhalla','Action RPG');
INSERT INTO "Platform" VALUES (1,'PlayStation');
INSERT INTO "Platform" VALUES (2,'Xbox');
INSERT INTO "Platform" VALUES (3,'Nintendo Switch');
INSERT INTO "Platform" VALUES (4,'PC');
INSERT INTO "Platform" VALUES (5,'Mobile');
INSERT INTO "Platform" VALUES (6,'VR');
INSERT INTO "Platform" VALUES (7,'Mac');
INSERT INTO "Platform" VALUES (8,'Linux');
INSERT INTO "Platform" VALUES (9,'Android');
INSERT INTO "Platform" VALUES (10,'iOS');
INSERT INTO "GamePlatform" VALUES (1,1,1);
INSERT INTO "GamePlatform" VALUES (2,1,4);
INSERT INTO "GamePlatform" VALUES (3,2,1);
INSERT INTO "GamePlatform" VALUES (4,2,2);
INSERT INTO "GamePlatform" VALUES (5,2,4);
INSERT INTO "GamePlatform" VALUES (6,3,3);
INSERT INTO "GamePlatform" VALUES (7,4,1);
INSERT INTO "GamePlatform" VALUES (8,4,2);
INSERT INTO "GamePlatform" VALUES (9,4,4);
INSERT INTO "GamePlatform" VALUES (10,5,1);
INSERT INTO "GamePlatform" VALUES (11,5,2);
INSERT INTO "GamePlatform" VALUES (12,5,4);
INSERT INTO "GamePlatform" VALUES (13,5,5);
INSERT INTO "GamePlatform" VALUES (14,6,3);
INSERT INTO "GamePlatform" VALUES (15,7,1);
INSERT INTO "GamePlatform" VALUES (16,7,2);
INSERT INTO "GamePlatform" VALUES (17,7,4);
INSERT INTO "GamePlatform" VALUES (18,7,5);
INSERT INTO "GamePlatform" VALUES (19,8,4);
INSERT INTO "GamePlatform" VALUES (20,8,5);
INSERT INTO "GamePlatform" VALUES (21,9,4);
INSERT INTO "GamePlatform" VALUES (22,10,1);
INSERT INTO "GamePlatform" VALUES (23,10,2);
INSERT INTO "GamePlatform" VALUES (24,10,4);
INSERT INTO "Warenhuis" VALUES (1,'ElectroWorld','123 Main Street, Cityville');
INSERT INTO "Warenhuis" VALUES (2,'TechGadgets','456 High Street, Tech City');
INSERT INTO "Warenhuis" VALUES (3,'Gaming Haven','789 Broad Street, Gametown');
INSERT INTO "Warenhuis" VALUES (4,'Digital Delights','101 Gadget Avenue, Techland');
INSERT INTO "Warenhuis" VALUES (5,'Electronics Emporium','202 Innovation Road, Electronics City');
INSERT INTO "GameCopy" VALUES (1,1,NULL,1);
INSERT INTO "GameCopy" VALUES (2,2,NULL,2);
INSERT INTO "GameCopy" VALUES (3,3,NULL,3);
INSERT INTO "GameCopy" VALUES (4,4,NULL,4);
INSERT INTO "GameCopy" VALUES (5,5,NULL,5);
INSERT INTO "GameCopy" VALUES (6,6,1,NULL);
INSERT INTO "GameCopy" VALUES (7,7,2,NULL);
INSERT INTO "GameCopy" VALUES (8,8,3,NULL);
INSERT INTO "GameCopy" VALUES (9,9,4,NULL);
INSERT INTO "GameCopy" VALUES (10,10,5,NULL);
INSERT INTO "GameCopy" VALUES (11,1,NULL,2);
INSERT INTO "GameCopy" VALUES (12,1,NULL,3);
INSERT INTO "GameCopy" VALUES (13,6,2,NULL);
INSERT INTO "GameCopy" VALUES (14,6,3,NULL);
INSERT INTO "GameCopy" VALUES (15,2,NULL,1);
INSERT INTO "GameCopy" VALUES (16,2,NULL,3);
INSERT INTO "GameCopy" VALUES (17,2,NULL,4);
INSERT INTO "GameCopy" VALUES (18,7,1,NULL);
INSERT INTO "GameCopy" VALUES (19,7,3,NULL);
INSERT INTO "GameCopy" VALUES (20,7,4,NULL);
INSERT INTO "GameCopy" VALUES (21,11,NULL,1);
INSERT INTO "GameCopy" VALUES (22,11,NULL,2);
INSERT INTO "GameCopy" VALUES (23,12,1,NULL);
INSERT INTO "GameCopy" VALUES (24,12,3,NULL);
INSERT INTO "Museum" VALUES (1,'Art Gallery',15.99,'123 Art Street, Artsville');
INSERT INTO "Museum" VALUES (2,'Natural History Museum',12.5,'456 Fossil Lane, History Town');
INSERT INTO "Museum" VALUES (3,'Science Center',18.75,'789 Innovation Avenue, Science City');
INSERT INTO "Museum" VALUES (4,'Space Exploration Museum',20.5,'101 Cosmos Road, Space City');
INSERT INTO "Museum" VALUES (5,'Technology Museum',14.25,'202 Gadget Boulevard, Techland');
INSERT INTO "Donatie" VALUES (1,100.0,1,'2023-01-15');
INSERT INTO "Donatie" VALUES (2,50.0,2,'2023-02-20');
INSERT INTO "Donatie" VALUES (3,200.0,3,'2023-03-10');
INSERT INTO "Donatie" VALUES (4,75.0,4,'2023-04-05');
INSERT INTO "Donatie" VALUES (5,150.0,5,'2023-05-12');
INSERT INTO "Bezoeker" VALUES (1,'Alice Johnson');
INSERT INTO "Bezoeker" VALUES (2,'Bob Smith');
INSERT INTO "Bezoeker" VALUES (3,'Charlie Brown');
INSERT INTO "Bezoeker" VALUES (4,'Diana Garcia');
INSERT INTO "Bezoeker" VALUES (5,'Evan Williams');
COMMIT;
