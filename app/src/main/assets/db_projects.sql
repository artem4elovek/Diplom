PRAGMA FOREIGN_KEYS = ON;

CREATE TABLE "Projects"(
	"idProject" INTEGER NOT NULL UNIQUE,
	"nameProject" TEXT NOT NULL UNIQUE,
	PRIMARY KEY("idProject" AUTOINCREMENT)
);


CREATE TABLE "ComponentsInProject"(
	"IdComponents" INTEGER NOT NULL,
	"posX" REAL,
	"posY" REAL,
	"rotation" REAL,
	"typeComponent" TEXT NOT NULL,
	"projectId" INTEGER NOT NULL,
	FOREIGN KEY("projectId") REFERENCES "Projects"("idProject")
	PRIMARY KEY("IdComponents" AUTOINCREMENT)
);

INSERT INTO "Projects" (nameProject)
VALUES('Стартовый проект');


INSERT INTO "ComponentsInProject" ("posX","posY","projectId","typeComponent","rotation" )
VALUES(100,100,1,1,0);

INSERT INTO "ComponentsInProject" ("posX","posY","projectId","typeComponent", "rotation" )
VALUES(200,200,1,1,0);

INSERT INTO "ComponentsInProject" ("posX","posY","projectId","typeComponent","rotation" )
VALUES(100,100,1,1,0),
(200,200,1,1,0);

UPDATE "Projects"
SET "nameProject" = 'Обновленный проект'
WHERE "idProject" = 1;

UPDATE "ComponentsInProject"
SET "posX" = 200.0,
	"posY" = 200.0,
	"rotation" = 200
WHERE "IdComponents" = 1;


