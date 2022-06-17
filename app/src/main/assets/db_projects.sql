PRAGMA FOREIGN_KEYS = ON;

CREATE TABLE "Projects"(
	"idProject" INTEGER NOT NULL UNIQUE,
	"nameProject" TEXT NOT NULL UNIQUE,
	PRIMARY KEY("idProject" AUTOINCREMENT)
);

CREATE TABLE "details" (
	"id"	INTEGER NOT NULL UNIQUE,
	"id_project"	INTEGER NOT NULL,
	"x_pos"	REAL NOT NULL,
	"y_pos"	REAL NOT NULL,
	"size"	INTEGER NOT NULL,
	"rotation"	INTEGER NOT NULL,
	"type"	INTEGER,
	FOREIGN KEY("id_project") REFERENCES "Projects"("idProject") ON DELETE CASCADE,
	PRIMARY KEY("id")
);

CREATE TABLE "dots_contact" (
	"id"	INTEGER NOT NULL UNIQUE,
	"id_detail"	INTEGER NOT NULL,
	"x_pos"	REAL NOT NULL,
	"y_pos"	REAL NOT NULL,
	FOREIGN KEY("id_detail") REFERENCES "details"("id")  ON DELETE CASCADE,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE "power" (
	"id"	INTEGER NOT NULL UNIQUE,
	"id_detail"	INTEGER NOT NULL UNIQUE,
	"powers"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("id_detail") REFERENCES "details"("id")  ON DELETE CASCADE
);
CREATE TABLE "resistor" (
	"id"	INTEGER NOT NULL UNIQUE,
	"id_detail"	INTEGER NOT NULL UNIQUE,
	"resistance"	REAL NOT NULL,
	FOREIGN KEY("id_detail") REFERENCES "details"("id")  ON DELETE CASCADE,
	PRIMARY KEY("id" AUTOINCREMENT)
);

INSERT INTO "Projects" (nameProject,idProject)
VALUES('Временный проект', 0);

INSERT INTO "Projects" (nameProject)
VALUES('Стартовый проект');

UPDATE "Projects"
SET "nameProject" = 'Обновленный проект'
WHERE "idProject" = 1;