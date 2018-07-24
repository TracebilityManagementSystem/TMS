use TMS;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS Version;
DROP TABLE IF EXISTS Characterization;
DROP TABLE IF EXISTS Constraints;
DROP TABLE IF EXISTS Artifact;
DROP TABLE IF EXISTS Trace;
DROP TABLE IF EXISTS Tracelink;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE Version (
  VersionId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  Owner varchar(100) NOT NULL,
  Modi_by varchar(100) NOT NULL,
  Modi_on DATETIME NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Characterization (
  CharId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  Type varchar(100) NOT NULL,
  Domain varchar(100) NOT NULL,
  Granularity varchar(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Constraints (
  ConsId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  Type varchar(100) NOT NULL,
  Value varchar(100) NOT NULL,
  Language varchar(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE Artifact (
  Art_uri varchar(100) NOT NULL PRIMARY KEY,
  VersionId INT NOT NULL,
  CharId INT NOT NULL,
  ConsId INT NOT NULL,  
  Title varchar(100) NOT NULL,
  Description varchar(8000)
	
  -- CONSTRAINT Artifact_fk1 FOREIGN KEY(VersionId)
  -- REFERENCES Version (VersionId)
  -- ON  UPDATE CASCADE ON DELETE CASCADE,
	
  -- CONSTRAINT Artifact_fk2 FOREIGN KEY(CharId)
  -- REFERENCES  Characterization (CharId)
  -- ON  UPDATE CASCADE ON DELETE CASCADE,
  
  -- CONSTRAINT Artifact_fk3 FOREIGN KEY(ConsId)
  -- REFERENCES Constraints (ConsId)
  -- ON  UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE Trace (
  Tra_uri varchar(100) NOT NULL PRIMARY KEY,
  VersionId INT NOT NULL,
  CharId INT NOT NULL,
  ConsId INT NOT NULL,
  
  CONSTRAINT Trace_fk1 FOREIGN KEY(VersionId)
  REFERENCES Version (VersionId)
  ON  UPDATE CASCADE ON DELETE CASCADE,
	
  CONSTRAINT Trace_fk2 FOREIGN KEY(CharId)
  REFERENCES  Characterization (CharId)
  ON  UPDATE CASCADE ON DELETE CASCADE,
  
  CONSTRAINT Trace_fk3 FOREIGN KEY(ConsId)
  REFERENCES Constraints (ConsId)
  ON  UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE Tracelink (
  LinkId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  sourceArtifact varchar(100) NOT NULL,
  targetArtifact varchar(100) NOT NULL,
  
  CONSTRAINT Tracelink_fk1 FOREIGN KEY(sourceArtifact)
  REFERENCES Artifact (Art_uri)
  ON  UPDATE CASCADE ON DELETE CASCADE,
  
  CONSTRAINT Tracelink_fk2 FOREIGN KEY(targetArtifact)
  REFERENCES Artifact (Art_uri)
  ON  UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;


