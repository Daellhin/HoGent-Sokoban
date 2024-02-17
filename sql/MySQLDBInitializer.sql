START TRANSACTION;
    create schema `sokoban` default character set latin1 collate latin1_general_cs;

    use sokoban;

    create table Speler(
        gebruikersnaam varchar(20) not null,
        wachtwoord varchar(20) not null,
        heeftAdminrechten boolean,
        naam varchar(20),
        voornaam varchar(20)
    );

    create table Spel(
        spelNaam varchar(20) not null,
        spelerNaam varchar(20) not null
    );

    create table Spelbord(
        spelNaam varchar(20) not null,
        volgnummer int not null
    );

    create table Vak(
        spelNaam varchar(20) not null,
        spelbordNummer int not null,
        rijPos int not null,
        kolPos int not null,
        vakType varchar(16) not null
    );

    create table Spelstuk(
        spelNaam varchar(20) not null,
        spelbordNummer int not null,
        rijPos int not null,
        kolPos int not null,
        spelstukType varchar(20) not null
    );

     -- primary keys
     alter table Speler
        add constraint PK_gebruikersnaam primary key(gebruikersnaam);
    alter table Spel
        add constraint PK_volgnummer primary key(spelNaam);
    alter table Spelbord
        add constraint PK_Spelbord primary key(spelNaam, volgnummer);
    alter table Vak
        add constraint PK_Vak primary key(spelNaam, spelbordNummer, rijPos, kolPos);
    alter table Spelstuk
        add constraint PK_Spelstuk primary key(spelNaam, spelbordNummer, rijPos, kolPos,spelstukType);

    -- foreign key
    alter table Spelbord
        add foreign key(spelNaam)references Spel(spelNaam);
    alter table Spel
        add foreign key(spelerNaam)references Speler(gebruikersNaam);
    alter table Vak
        add foreign key(spelNaam, spelbordNummer)references Spelbord(spelNaam, volgnummer);
    alter table Spelstuk
        add foreign key(spelNaam, spelbordNummer)references Spelbord(spelNaam, volgnummer);

    -- DB Initializer
    -- Creatie van onze admin en gewone speler
    Insert into Speler
        Values ('SpelerAdmin', 'Pa123456', true, '', ''), ('Speler00', 'Pa123456', false, 'Clauws', 'Robin');

    -- Creatie van onze 2 spellen (Spelnaam, gebruikersnaam van de speler)
    Insert into Spel
        Values ('Spel1', 'SpelerAdmin'), ('Spel2', 'SpelerAdmin'),  ('Spel3', 'SpelerAdmin');

    -- Creatie van onze 3 spelborden (spelnaam, spelbordnummer, startpositieX en startpositie Y)
    Insert into Spelbord
        Values('Spel1', 0), ('Spel1', 1), ('Spel2', 0),('Spel2',1), ('Spel3', 0),('Spel3',1),('Spel3',2);

    -- Creatie van onze vakken voor de 3 spelborden
    Insert into Vak
    Values
        ('Spel1', 0, 0, 0, 'muur'),('Spel1', 0, 0, 1, 'muur'),('Spel1', 0, 0, 2, 'muur'),('Spel1', 0, 0, 3, 'muur'),('Spel1', 0, 0, 4, 'muur'),('Spel1', 0, 0, 5, 'veld'),('Spel1', 0, 0, 6, 'veld'),('Spel1', 0, 0, 7, 'veld'),('Spel1', 0, 0, 8, 'veld'),('Spel1', 0, 0, 9, 'veld'),
        ('Spel1', 0, 1, 0, 'muur'),('Spel1', 0, 1, 1, 'veld'),('Spel1', 0, 1, 2, 'doel'),('Spel1', 0, 1, 3, 'veld'),('Spel1', 0, 1, 4, 'muur'),('Spel1', 0, 1, 5, 'veld'),('Spel1', 0, 1, 6, 'veld'),('Spel1', 0, 1, 7, 'veld'),('Spel1', 0, 1, 8, 'veld'),('Spel1', 0, 1, 9, 'veld'),
        ('Spel1', 0, 2, 0, 'muur'),('Spel1', 0, 2, 1, 'veld'),('Spel1', 0, 2, 2, 'veld'),('Spel1', 0, 2, 3, 'veld'),('Spel1', 0, 2, 4, 'muur'),('Spel1', 0, 2, 5, 'veld'),('Spel1', 0, 2, 6, 'veld'),('Spel1', 0, 2, 7, 'veld'),('Spel1', 0, 2, 8, 'veld'),('Spel1', 0, 2, 9, 'veld'),
        ('Spel1', 0, 3, 0, 'muur'),('Spel1', 0, 3, 1, 'veld'),('Spel1', 0, 3, 2, 'veld'),('Spel1', 0, 3, 3, 'veld'),('Spel1', 0, 3, 4, 'muur'),('Spel1', 0, 3, 5, 'veld'),('Spel1', 0, 3, 6, 'veld'),('Spel1', 0, 3, 7, 'veld'),('Spel1', 0, 3, 8, 'veld'),('Spel1', 0, 3, 9, 'veld'),
        ('Spel1', 0, 4, 0, 'muur'),('Spel1', 0, 4, 1, 'veld'),('Spel1', 0, 4, 2, 'veld'),('Spel1', 0, 4, 3, 'veld'),('Spel1', 0, 4, 4, 'muur'),('Spel1', 0, 4, 5, 'veld'),('Spel1', 0, 4, 6, 'veld'),('Spel1', 0, 4, 7, 'veld'),('Spel1', 0, 4, 8, 'veld'),('Spel1', 0, 4, 9, 'veld'),
        ('Spel1', 0, 5, 0, 'muur'),('Spel1', 0, 5, 1, 'muur'),('Spel1', 0, 5, 2, 'muur'),('Spel1', 0, 5, 3, 'muur'),('Spel1', 0, 5, 4, 'muur'),('Spel1', 0, 5, 5, 'veld'),('Spel1', 0, 5, 6, 'veld'),('Spel1', 0, 5, 7, 'veld'),('Spel1', 0, 5, 8, 'veld'),('Spel1', 0, 5, 9, 'veld'),
        ('Spel1', 0, 6, 0, 'veld'),('Spel1', 0, 6, 1, 'veld'),('Spel1', 0, 6, 2, 'veld'),('Spel1', 0, 6, 3, 'veld'),('Spel1', 0, 6, 4, 'veld'),('Spel1', 0, 6, 5, 'veld'),('Spel1', 0, 6, 6, 'veld'),('Spel1', 0, 6, 7, 'veld'),('Spel1', 0, 6, 8, 'veld'),('Spel1', 0, 6, 9, 'veld'),
        ('Spel1', 0, 7, 0, 'veld'),('Spel1', 0, 7, 1, 'veld'),('Spel1', 0, 7, 2, 'veld'),('Spel1', 0, 7, 3, 'veld'),('Spel1', 0, 7, 4, 'veld'),('Spel1', 0, 7, 5, 'veld'),('Spel1', 0, 7, 6, 'veld'),('Spel1', 0, 7, 7, 'veld'),('Spel1', 0, 7, 8, 'veld'),('Spel1', 0, 7, 9, 'veld'),
        ('Spel1', 0, 8, 0, 'veld'),('Spel1', 0, 8, 1, 'veld'),('Spel1', 0, 8, 2, 'veld'),('Spel1', 0, 8, 3, 'veld'),('Spel1', 0, 8, 4, 'veld'),('Spel1', 0, 8, 5, 'veld'),('Spel1', 0, 8, 6, 'veld'),('Spel1', 0, 8, 7, 'veld'),('Spel1', 0, 8, 8, 'veld'),('Spel1', 0, 8, 9, 'veld'),
        ('Spel1', 0, 9, 0, 'veld'),('Spel1', 0, 9, 1, 'veld'),('Spel1', 0, 9, 2, 'veld'),('Spel1', 0, 9, 3, 'veld'),('Spel1', 0, 9, 4, 'veld'),('Spel1', 0, 9, 5, 'veld'),('Spel1', 0, 9, 6, 'veld'),('Spel1', 0, 9, 7, 'veld'),('Spel1', 0, 9, 8, 'veld'),('Spel1', 0, 9, 9, 'veld');

    Insert into Vak
        Values
        ('Spel1', 1, 0, 0, 'muur'),('Spel1', 1, 0, 1, 'muur'),('Spel1', 1, 0, 2, 'muur'),('Spel1', 1, 0, 3, 'muur'),('Spel1', 1, 0, 4, 'muur'),('Spel1', 1, 0, 5, 'muur'),('Spel1', 1, 0, 6, 'veld'),('Spel1', 1, 0, 7, 'veld'),('Spel1', 1, 0, 8, 'veld'),('Spel1', 1, 0, 9, 'veld'),
        ('Spel1', 1, 1, 0, 'muur'),('Spel1', 1, 1, 1, 'doel'),('Spel1', 1, 1, 2, 'veld'),('Spel1', 1, 1, 3, 'veld'),('Spel1', 1, 1, 4, 'veld'),('Spel1', 1, 1, 5, 'muur'),('Spel1', 1, 1, 6, 'veld'),('Spel1', 1, 1, 7, 'veld'),('Spel1', 1, 1, 8, 'veld'),('Spel1', 1, 1, 9, 'veld'),
        ('Spel1', 1, 2, 0, 'muur'),('Spel1', 1, 2, 1, 'muur'),('Spel1', 1, 2, 2, 'muur'),('Spel1', 1, 2, 3, 'muur'),('Spel1', 1, 2, 4, 'muur'),('Spel1', 1, 2, 5, 'muur'),('Spel1', 1, 2, 6, 'veld'),('Spel1', 1, 2, 7, 'veld'),('Spel1', 1, 2, 8, 'veld'),('Spel1', 1, 2, 9, 'veld'),
        ('Spel1', 1, 3, 0, 'veld'),('Spel1', 1, 3, 1, 'veld'),('Spel1', 1, 3, 2, 'veld'),('Spel1', 1, 3, 3, 'veld'),('Spel1', 1, 3, 4, 'veld'),('Spel1', 1, 3, 5, 'veld'),('Spel1', 1, 3, 6, 'veld'),('Spel1', 1, 3, 7, 'veld'),('Spel1', 1, 3, 8, 'veld'),('Spel1', 1, 3, 9, 'veld'),
        ('Spel1', 1, 4, 0, 'veld'),('Spel1', 1, 4, 1, 'veld'),('Spel1', 1, 4, 2, 'veld'),('Spel1', 1, 4, 3, 'veld'),('Spel1', 1, 4, 4, 'veld'),('Spel1', 1, 4, 5, 'veld'),('Spel1', 1, 4, 6, 'veld'),('Spel1', 1, 4, 7, 'veld'),('Spel1', 1, 4, 8, 'veld'),('Spel1', 1, 4, 9, 'veld'),
        ('Spel1', 1, 5, 0, 'veld'),('Spel1', 1, 5, 1, 'veld'),('Spel1', 1, 5, 2, 'veld'),('Spel1', 1, 5, 3, 'veld'),('Spel1', 1, 5, 4, 'veld'),('Spel1', 1, 5, 5, 'veld'),('Spel1', 1, 5, 6, 'veld'),('Spel1', 1, 5, 7, 'veld'),('Spel1', 1, 5, 8, 'veld'),('Spel1', 1, 5, 9, 'veld'),
        ('Spel1', 1, 6, 0, 'veld'),('Spel1', 1, 6, 1, 'veld'),('Spel1', 1, 6, 2, 'veld'),('Spel1', 1, 6, 3, 'veld'),('Spel1', 1, 6, 4, 'veld'),('Spel1', 1, 6, 5, 'veld'),('Spel1', 1, 6, 6, 'veld'),('Spel1', 1, 6, 7, 'veld'),('Spel1', 1, 6, 8, 'veld'),('Spel1', 1, 6, 9, 'veld'),
        ('Spel1', 1, 7, 0, 'veld'),('Spel1', 1, 7, 1, 'veld'),('Spel1', 1, 7, 2, 'veld'),('Spel1', 1, 7, 3, 'veld'),('Spel1', 1, 7, 4, 'veld'),('Spel1', 1, 7, 5, 'veld'),('Spel1', 1, 7, 6, 'veld'),('Spel1', 1, 7, 7, 'veld'),('Spel1', 1, 7, 8, 'veld'),('Spel1', 1, 7, 9, 'veld'),
        ('Spel1', 1, 8, 0, 'veld'),('Spel1', 1, 8, 1, 'veld'),('Spel1', 1, 8, 2, 'veld'),('Spel1', 1, 8, 3, 'veld'),('Spel1', 1, 8, 4, 'veld'),('Spel1', 1, 8, 5, 'veld'),('Spel1', 1, 8, 6, 'veld'),('Spel1', 1, 8, 7, 'veld'),('Spel1', 1, 8, 8, 'veld'),('Spel1', 1, 8, 9, 'veld'),
        ('Spel1', 1, 9, 0, 'veld'),('Spel1', 1, 9, 1, 'veld'),('Spel1', 1, 9, 2, 'veld'),('Spel1', 1, 9, 3, 'veld'),('Spel1', 1, 9, 4, 'veld'),('Spel1', 1, 9, 5, 'veld'),('Spel1', 1, 9, 6, 'veld'),('Spel1', 1, 9, 7, 'veld'),('Spel1', 1, 9, 8, 'veld'),('Spel1', 1, 9, 9, 'veld');

    Insert into Vak
    Values
        ('Spel2', 0, 0, 0, 'muur'),('Spel2', 0, 0, 1, 'muur'),('Spel2', 0, 0, 2, 'muur'),('Spel2', 0, 0, 3, 'muur'),('Spel2', 0, 0, 4, 'muur'),('Spel2', 0, 0, 5, 'veld'),('Spel2', 0, 0, 6, 'veld'),('Spel2', 0, 0, 7, 'veld'),('Spel2', 0, 0, 8, 'veld'),('Spel2', 0, 0, 9, 'veld'),
        ('Spel2', 0, 1, 0, 'muur'),('Spel2', 0, 1, 1, 'doel'),('Spel2', 0, 1, 2, 'veld'),('Spel2', 0, 1, 3, 'doel'),('Spel2', 0, 1, 4, 'muur'),('Spel2', 0, 1, 5, 'veld'),('Spel2', 0, 1, 6, 'veld'),('Spel2', 0, 1, 7, 'veld'),('Spel2', 0, 1, 8, 'veld'),('Spel2', 0, 1, 9, 'veld'),
        ('Spel2', 0, 2, 0, 'muur'),('Spel2', 0, 2, 1, 'veld'),('Spel2', 0, 2, 2, 'veld'),('Spel2', 0, 2, 3, 'veld'),('Spel2', 0, 2, 4, 'muur'),('Spel2', 0, 2, 5, 'veld'),('Spel2', 0, 2, 6, 'veld'),('Spel2', 0, 2, 7, 'veld'),('Spel2', 0, 2, 8, 'veld'),('Spel2', 0, 2, 9, 'veld'),
        ('Spel2', 0, 3, 0, 'muur'),('Spel2', 0, 3, 1, 'veld'),('Spel2', 0, 3, 2, 'veld'),('Spel2', 0, 3, 3, 'veld'),('Spel2', 0, 3, 4, 'muur'),('Spel2', 0, 3, 5, 'veld'),('Spel2', 0, 3, 6, 'veld'),('Spel2', 0, 3, 7, 'veld'),('Spel2', 0, 3, 8, 'veld'),('Spel2', 0, 3, 9, 'veld'),
        ('Spel2', 0, 4, 0, 'muur'),('Spel2', 0, 4, 1, 'veld'),('Spel2', 0, 4, 2, 'veld'),('Spel2', 0, 4, 3, 'veld'),('Spel2', 0, 4, 4, 'muur'),('Spel2', 0, 4, 5, 'veld'),('Spel2', 0, 4, 6, 'veld'),('Spel2', 0, 4, 7, 'veld'),('Spel2', 0, 4, 8, 'veld'),('Spel2', 0, 4, 9, 'veld'),
        ('Spel2', 0, 5, 0, 'muur'),('Spel2', 0, 5, 1, 'veld'),('Spel2', 0, 5, 2, 'veld'),('Spel2', 0, 5, 3, 'veld'),('Spel2', 0, 5, 4, 'muur'),('Spel2', 0, 5, 5, 'veld'),('Spel2', 0, 5, 6, 'veld'),('Spel2', 0, 5, 7, 'veld'),('Spel2', 0, 5, 8, 'veld'),('Spel2', 0, 5, 9, 'veld'),
        ('Spel2', 0, 6, 0, 'muur'),('Spel2', 0, 6, 1, 'veld'),('Spel2', 0, 6, 2, 'veld'),('Spel2', 0, 6, 3, 'veld'),('Spel2', 0, 6, 4, 'muur'),('Spel2', 0, 6, 5, 'veld'),('Spel2', 0, 6, 6, 'veld'),('Spel2', 0, 6, 7, 'veld'),('Spel2', 0, 6, 8, 'veld'),('Spel2', 0, 6, 9, 'veld'),
        ('Spel2', 0, 7, 0, 'muur'),('Spel2', 0, 7, 1, 'veld'),('Spel2', 0, 7, 2, 'veld'),('Spel2', 0, 7, 3, 'veld'),('Spel2', 0, 7, 4, 'muur'),('Spel2', 0, 7, 5, 'veld'),('Spel2', 0, 7, 6, 'veld'),('Spel2', 0, 7, 7, 'veld'),('Spel2', 0, 7, 8, 'veld'),('Spel2', 0, 7, 9, 'veld'),
        ('Spel2', 0, 8, 0, 'muur'),('Spel2', 0, 8, 1, 'veld'),('Spel2', 0, 8, 2, 'veld'),('Spel2', 0, 8, 3, 'veld'),('Spel2', 0, 8, 4, 'muur'),('Spel2', 0, 8, 5, 'veld'),('Spel2', 0, 8, 6, 'veld'),('Spel2', 0, 8, 7, 'veld'),('Spel2', 0, 8, 8, 'veld'),('Spel2', 0, 8, 9, 'veld'),
        ('Spel2', 0, 9, 0, 'muur'),('Spel2', 0, 9, 1, 'muur'),('Spel2', 0, 9, 2, 'muur'),('Spel2', 0, 9, 3, 'muur'),('Spel2', 0, 9, 4, 'muur'),('Spel2', 0, 9, 5, 'veld'),('Spel2', 0, 9, 6, 'veld'),('Spel2', 0, 9, 7, 'veld'),('Spel2', 0, 9, 8, 'veld'),('Spel2', 0, 9, 9, 'veld');

    Insert into Vak
    Values
        ('Spel2', 1, 0, 0, 'muur'),('Spel2', 1, 0, 1, 'muur'),('Spel2', 1, 0, 2, 'muur'),('Spel2', 1, 0, 3, 'muur'),('Spel2', 1, 0, 4, 'muur'),('Spel2', 1, 0, 5, 'muur'),('Spel2', 1, 0, 6, 'muur'),('Spel2', 1, 0, 7, 'muur'),('Spel2', 1, 0, 8, 'muur'),('Spel2', 1, 0, 9, 'muur'),
        ('Spel2', 1, 1, 0, 'muur'),('Spel2', 1, 1, 1, 'veld'),('Spel2', 1, 1, 2, 'muur'),('Spel2', 1, 1, 3, 'muur'),('Spel2', 1, 1, 4, 'veld'),('Spel2', 1, 1, 5, 'veld'),('Spel2', 1, 1, 6, 'veld'),('Spel2', 1, 1, 7, 'veld'),('Spel2', 1, 1, 8, 'veld'),('Spel2', 1, 1, 9, 'muur'),
        ('Spel2', 1, 2, 0, 'muur'),('Spel2', 1, 2, 1, 'doel'),('Spel2', 1, 2, 2, 'muur'),('Spel2', 1, 2, 3, 'veld'),('Spel2', 1, 2, 4, 'veld'),('Spel2', 1, 2, 5, 'veld'),('Spel2', 1, 2, 6, 'veld'),('Spel2', 1, 2, 7, 'veld'),('Spel2', 1, 2, 8, 'veld'),('Spel2', 1, 2, 9, 'muur'),
        ('Spel2', 1, 3, 0, 'muur'),('Spel2', 1, 3, 1, 'veld'),('Spel2', 1, 3, 2, 'muur'),('Spel2', 1, 3, 3, 'veld'),('Spel2', 1, 3, 4, 'veld'),('Spel2', 1, 3, 5, 'veld'),('Spel2', 1, 3, 6, 'veld'),('Spel2', 1, 3, 7, 'veld'),('Spel2', 1, 3, 8, 'veld'),('Spel2', 1, 3, 9, 'muur'),
        ('Spel2', 1, 4, 0, 'muur'),('Spel2', 1, 4, 1, 'veld'),('Spel2', 1, 4, 2, 'muur'),('Spel2', 1, 4, 3, 'veld'),('Spel2', 1, 4, 4, 'veld'),('Spel2', 1, 4, 5, 'veld'),('Spel2', 1, 4, 6, 'veld'),('Spel2', 1, 4, 7, 'veld'),('Spel2', 1, 4, 8, 'veld'),('Spel2', 1, 4, 9, 'muur'),
        ('Spel2', 1, 5, 0, 'muur'),('Spel2', 1, 5, 1, 'veld'),('Spel2', 1, 5, 2, 'veld'),('Spel2', 1, 5, 3, 'veld'),('Spel2', 1, 5, 4, 'veld'),('Spel2', 1, 5, 5, 'veld'),('Spel2', 1, 5, 6, 'muur'),('Spel2', 1, 5, 7, 'veld'),('Spel2', 1, 5, 8, 'veld'),('Spel2', 1, 5, 9, 'muur'),
        ('Spel2', 1, 6, 0, 'muur'),('Spel2', 1, 6, 1, 'veld'),('Spel2', 1, 6, 2, 'veld'),('Spel2', 1, 6, 3, 'veld'),('Spel2', 1, 6, 4, 'veld'),('Spel2', 1, 6, 5, 'veld'),('Spel2', 1, 6, 6, 'muur'),('Spel2', 1, 6, 7, 'veld'),('Spel2', 1, 6, 8, 'veld'),('Spel2', 1, 6, 9, 'muur'),
        ('Spel2', 1, 7, 0, 'muur'),('Spel2', 1, 7, 1, 'veld'),('Spel2', 1, 7, 2, 'veld'),('Spel2', 1, 7, 3, 'veld'),('Spel2', 1, 7, 4, 'veld'),('Spel2', 1, 7, 5, 'veld'),('Spel2', 1, 7, 6, 'muur'),('Spel2', 1, 7, 7, 'veld'),('Spel2', 1, 7, 8, 'veld'),('Spel2', 1, 7, 9, 'muur'),
        ('Spel2', 1, 8, 0, 'muur'),('Spel2', 1, 8, 1, 'veld'),('Spel2', 1, 8, 2, 'muur'),('Spel2', 1, 8, 3, 'veld'),('Spel2', 1, 8, 4, 'veld'),('Spel2', 1, 8, 5, 'veld'),('Spel2', 1, 8, 6, 'muur'),('Spel2', 1, 8, 7, 'veld'),('Spel2', 1, 8, 8, 'doel'),('Spel2', 1, 8, 9, 'muur'),
        ('Spel2', 1, 9, 0, 'muur'),('Spel2', 1, 9, 1, 'muur'),('Spel2', 1, 9, 2, 'muur'),('Spel2', 1, 9, 3, 'muur'),('Spel2', 1, 9, 4, 'muur'),('Spel2', 1, 9, 5, 'muur'),('Spel2', 1, 9, 6, 'muur'),('Spel2', 1, 9, 7, 'muur'),('Spel2', 1, 9, 8, 'muur'),('Spel2', 1, 9, 9, 'muur');

    Insert into Vak
    Values
        ('Spel3', 0, 0, 0, 'muur'),('Spel3', 0, 0, 1, 'veld'),('Spel3', 0, 0, 2, 'veld'),('Spel3', 0, 0, 3, 'veld'),('Spel3', 0, 0, 4, 'veld'),('Spel3', 0, 0, 5, 'veld'),('Spel3', 0, 0, 6, 'veld'),('Spel3', 0, 0, 7, 'veld'),('Spel3', 0, 0, 8, 'veld'),('Spel3', 0, 0, 9, 'veld'),
        ('Spel3', 0, 1, 0, 'veld'),('Spel3', 0, 1, 1, 'veld'),('Spel3', 0, 1, 2, 'veld'),('Spel3', 0, 1, 3, 'veld'),('Spel3', 0, 1, 4, 'veld'),('Spel3', 0, 1, 5, 'veld'),('Spel3', 0, 1, 6, 'veld'),('Spel3', 0, 1, 7, 'veld'),('Spel3', 0, 1, 8, 'veld'),('Spel3', 0, 1, 9, 'veld'),
        ('Spel3', 0, 2, 0, 'veld'),('Spel3', 0, 2, 1, 'doel'),('Spel3', 0, 2, 2, 'veld'),('Spel3', 0, 2, 3, 'veld'),('Spel3', 0, 2, 4, 'veld'),('Spel3', 0, 2, 5, 'veld'),('Spel3', 0, 2, 6, 'veld'),('Spel3', 0, 2, 7, 'veld'),('Spel3', 0, 2, 8, 'veld'),('Spel3', 0, 2, 9, 'veld'),
        ('Spel3', 0, 3, 0, 'veld'),('Spel3', 0, 3, 1, 'veld'),('Spel3', 0, 3, 2, 'veld'),('Spel3', 0, 3, 3, 'veld'),('Spel3', 0, 3, 4, 'veld'),('Spel3', 0, 3, 5, 'veld'),('Spel3', 0, 3, 6, 'veld'),('Spel3', 0, 3, 7, 'veld'),('Spel3', 0, 3, 8, 'veld'),('Spel3', 0, 3, 9, 'veld'),
        ('Spel3', 0, 4, 0, 'veld'),('Spel3', 0, 4, 1, 'veld'),('Spel3', 0, 4, 2, 'veld'),('Spel3', 0, 4, 3, 'veld'),('Spel3', 0, 4, 4, 'veld'),('Spel3', 0, 4, 5, 'veld'),('Spel3', 0, 4, 6, 'veld'),('Spel3', 0, 4, 7, 'veld'),('Spel3', 0, 4, 8, 'veld'),('Spel3', 0, 4, 9, 'veld'),
        ('Spel3', 0, 5, 0, 'veld'),('Spel3', 0, 5, 1, 'veld'),('Spel3', 0, 5, 2, 'veld'),('Spel3', 0, 5, 3, 'veld'),('Spel3', 0, 5, 4, 'veld'),('Spel3', 0, 5, 5, 'veld'),('Spel3', 0, 5, 6, 'veld'),('Spel3', 0, 5, 7, 'veld'),('Spel3', 0, 5, 8, 'veld'),('Spel3', 0, 5, 9, 'veld'),
        ('Spel3', 0, 6, 0, 'veld'),('Spel3', 0, 6, 1, 'veld'),('Spel3', 0, 6, 2, 'veld'),('Spel3', 0, 6, 3, 'veld'),('Spel3', 0, 6, 4, 'veld'),('Spel3', 0, 6, 5, 'veld'),('Spel3', 0, 6, 6, 'veld'),('Spel3', 0, 6, 7, 'veld'),('Spel3', 0, 6, 8, 'veld'),('Spel3', 0, 6, 9, 'veld'),
        ('Spel3', 0, 7, 0, 'veld'),('Spel3', 0, 7, 1, 'veld'),('Spel3', 0, 7, 2, 'veld'),('Spel3', 0, 7, 3, 'veld'),('Spel3', 0, 7, 4, 'veld'),('Spel3', 0, 7, 5, 'veld'),('Spel3', 0, 7, 6, 'veld'),('Spel3', 0, 7, 7, 'veld'),('Spel3', 0, 7, 8, 'veld'),('Spel3', 0, 7, 9, 'veld'),
        ('Spel3', 0, 8, 0, 'veld'),('Spel3', 0, 8, 1, 'veld'),('Spel3', 0, 8, 2, 'veld'),('Spel3', 0, 8, 3, 'veld'),('Spel3', 0, 8, 4, 'veld'),('Spel3', 0, 8, 5, 'veld'),('Spel3', 0, 8, 6, 'veld'),('Spel3', 0, 8, 7, 'veld'),('Spel3', 0, 8, 8, 'veld'),('Spel3', 0, 8, 9, 'veld'),
        ('Spel3', 0, 9, 0, 'veld'),('Spel3', 0, 9, 1, 'veld'),('Spel3', 0, 9, 2, 'veld'),('Spel3', 0, 9, 3, 'veld'),('Spel3', 0, 9, 4, 'veld'),('Spel3', 0, 9, 5, 'veld'),('Spel3', 0, 9, 6, 'veld'),('Spel3', 0, 9, 7, 'veld'),('Spel3', 0, 9, 8, 'veld'),('Spel3', 0, 9, 9, 'veld');

    Insert into Vak
    Values
        ('Spel3', 1, 0, 0, 'muur'),('Spel3', 1, 0, 1, 'veld'),('Spel3', 1, 0, 2, 'veld'),('Spel3', 1, 0, 3, 'veld'),('Spel3', 1, 0, 4, 'veld'),('Spel3', 1, 0, 5, 'veld'),('Spel3', 1, 0, 6, 'veld'),('Spel3', 1, 0, 7, 'veld'),('Spel3', 1, 0, 8, 'veld'),('Spel3', 1, 0, 9, 'veld'),
        ('Spel3', 1, 1, 0, 'muur'),('Spel3', 1, 1, 1, 'veld'),('Spel3', 1, 1, 2, 'veld'),('Spel3', 1, 1, 3, 'veld'),('Spel3', 1, 1, 4, 'veld'),('Spel3', 1, 1, 5, 'veld'),('Spel3', 1, 1, 6, 'veld'),('Spel3', 1, 1, 7, 'veld'),('Spel3', 1, 1, 8, 'veld'),('Spel3', 1, 1, 9, 'veld'),
        ('Spel3', 1, 2, 0, 'veld'),('Spel3', 1, 2, 1, 'doel'),('Spel3', 1, 2, 2, 'veld'),('Spel3', 1, 2, 3, 'veld'),('Spel3', 1, 2, 4, 'veld'),('Spel3', 1, 2, 5, 'veld'),('Spel3', 1, 2, 6, 'veld'),('Spel3', 1, 2, 7, 'veld'),('Spel3', 1, 2, 8, 'veld'),('Spel3', 1, 2, 9, 'veld'),
        ('Spel3', 1, 3, 0, 'veld'),('Spel3', 1, 3, 1, 'veld'),('Spel3', 1, 3, 2, 'veld'),('Spel3', 1, 3, 3, 'veld'),('Spel3', 1, 3, 4, 'veld'),('Spel3', 1, 3, 5, 'veld'),('Spel3', 1, 3, 6, 'veld'),('Spel3', 1, 3, 7, 'veld'),('Spel3', 1, 3, 8, 'veld'),('Spel3', 1, 3, 9, 'veld'),
        ('Spel3', 1, 4, 0, 'veld'),('Spel3', 1, 4, 1, 'veld'),('Spel3', 1, 4, 2, 'veld'),('Spel3', 1, 4, 3, 'veld'),('Spel3', 1, 4, 4, 'veld'),('Spel3', 1, 4, 5, 'veld'),('Spel3', 1, 4, 6, 'veld'),('Spel3', 1, 4, 7, 'veld'),('Spel3', 1, 4, 8, 'veld'),('Spel3', 1, 4, 9, 'veld'),
        ('Spel3', 1, 5, 0, 'veld'),('Spel3', 1, 5, 1, 'veld'),('Spel3', 1, 5, 2, 'veld'),('Spel3', 1, 5, 3, 'veld'),('Spel3', 1, 5, 4, 'veld'),('Spel3', 1, 5, 5, 'veld'),('Spel3', 1, 5, 6, 'veld'),('Spel3', 1, 5, 7, 'veld'),('Spel3', 1, 5, 8, 'veld'),('Spel3', 1, 5, 9, 'veld'),
        ('Spel3', 1, 6, 0, 'veld'),('Spel3', 1, 6, 1, 'veld'),('Spel3', 1, 6, 2, 'veld'),('Spel3', 1, 6, 3, 'veld'),('Spel3', 1, 6, 4, 'veld'),('Spel3', 1, 6, 5, 'veld'),('Spel3', 1, 6, 6, 'veld'),('Spel3', 1, 6, 7, 'veld'),('Spel3', 1, 6, 8, 'veld'),('Spel3', 1, 6, 9, 'veld'),
        ('Spel3', 1, 7, 0, 'veld'),('Spel3', 1, 7, 1, 'veld'),('Spel3', 1, 7, 2, 'veld'),('Spel3', 1, 7, 3, 'veld'),('Spel3', 1, 7, 4, 'veld'),('Spel3', 1, 7, 5, 'veld'),('Spel3', 1, 7, 6, 'veld'),('Spel3', 1, 7, 7, 'veld'),('Spel3', 1, 7, 8, 'veld'),('Spel3', 1, 7, 9, 'veld'),
        ('Spel3', 1, 8, 0, 'veld'),('Spel3', 1, 8, 1, 'veld'),('Spel3', 1, 8, 2, 'veld'),('Spel3', 1, 8, 3, 'veld'),('Spel3', 1, 8, 4, 'veld'),('Spel3', 1, 8, 5, 'veld'),('Spel3', 1, 8, 6, 'veld'),('Spel3', 1, 8, 7, 'veld'),('Spel3', 1, 8, 8, 'veld'),('Spel3', 1, 8, 9, 'veld'),
        ('Spel3', 1, 9, 0, 'veld'),('Spel3', 1, 9, 1, 'veld'),('Spel3', 1, 9, 2, 'veld'),('Spel3', 1, 9, 3, 'veld'),('Spel3', 1, 9, 4, 'veld'),('Spel3', 1, 9, 5, 'veld'),('Spel3', 1, 9, 6, 'veld'),('Spel3', 1, 9, 7, 'veld'),('Spel3', 1, 9, 8, 'veld'),('Spel3', 1, 9, 9, 'veld');

    Insert into Vak
    Values
        ('Spel3', 2, 0, 0, 'muur'),('Spel3', 2, 0, 1, 'veld'),('Spel3', 2, 0, 2, 'veld'),('Spel3', 2, 0, 3, 'veld'),('Spel3', 2, 0, 4, 'veld'),('Spel3', 2, 0, 5, 'veld'),('Spel3', 2, 0, 6, 'veld'),('Spel3', 2, 0, 7, 'veld'),('Spel3', 2, 0, 8, 'veld'),('Spel3', 2, 0, 9, 'veld'),
        ('Spel3', 2, 1, 0, 'muur'),('Spel3', 2, 1, 1, 'veld'),('Spel3', 2, 1, 2, 'veld'),('Spel3', 2, 1, 3, 'veld'),('Spel3', 2, 1, 4, 'veld'),('Spel3', 2, 1, 5, 'veld'),('Spel3', 2, 1, 6, 'veld'),('Spel3', 2, 1, 7, 'veld'),('Spel3', 2, 1, 8, 'veld'),('Spel3', 2, 1, 9, 'veld'),
        ('Spel3', 2, 2, 0, 'muur'),('Spel3', 2, 2, 1, 'doel'),('Spel3', 2, 2, 2, 'veld'),('Spel3', 2, 2, 3, 'veld'),('Spel3', 2, 2, 4, 'veld'),('Spel3', 2, 2, 5, 'veld'),('Spel3', 2, 2, 6, 'veld'),('Spel3', 2, 2, 7, 'veld'),('Spel3', 2, 2, 8, 'veld'),('Spel3', 2, 2, 9, 'veld'),
        ('Spel3', 2, 3, 0, 'veld'),('Spel3', 2, 3, 1, 'veld'),('Spel3', 2, 3, 2, 'veld'),('Spel3', 2, 3, 3, 'veld'),('Spel3', 2, 3, 4, 'veld'),('Spel3', 2, 3, 5, 'veld'),('Spel3', 2, 3, 6, 'veld'),('Spel3', 2, 3, 7, 'veld'),('Spel3', 2, 3, 8, 'veld'),('Spel3', 2, 3, 9, 'veld'),
        ('Spel3', 2, 4, 0, 'veld'),('Spel3', 2, 4, 1, 'veld'),('Spel3', 2, 4, 2, 'veld'),('Spel3', 2, 4, 3, 'veld'),('Spel3', 2, 4, 4, 'veld'),('Spel3', 2, 4, 5, 'veld'),('Spel3', 2, 4, 6, 'veld'),('Spel3', 2, 4, 7, 'veld'),('Spel3', 2, 4, 8, 'veld'),('Spel3', 2, 4, 9, 'veld'),
        ('Spel3', 2, 5, 0, 'veld'),('Spel3', 2, 5, 1, 'veld'),('Spel3', 2, 5, 2, 'veld'),('Spel3', 2, 5, 3, 'veld'),('Spel3', 2, 5, 4, 'veld'),('Spel3', 2, 5, 5, 'veld'),('Spel3', 2, 5, 6, 'veld'),('Spel3', 2, 5, 7, 'veld'),('Spel3', 2, 5, 8, 'veld'),('Spel3', 2, 5, 9, 'veld'),
        ('Spel3', 2, 6, 0, 'veld'),('Spel3', 2, 6, 1, 'veld'),('Spel3', 2, 6, 2, 'veld'),('Spel3', 2, 6, 3, 'veld'),('Spel3', 2, 6, 4, 'veld'),('Spel3', 2, 6, 5, 'veld'),('Spel3', 2, 6, 6, 'veld'),('Spel3', 2, 6, 7, 'veld'),('Spel3', 2, 6, 8, 'veld'),('Spel3', 2, 6, 9, 'veld'),
        ('Spel3', 2, 7, 0, 'veld'),('Spel3', 2, 7, 1, 'veld'),('Spel3', 2, 7, 2, 'veld'),('Spel3', 2, 7, 3, 'veld'),('Spel3', 2, 7, 4, 'veld'),('Spel3', 2, 7, 5, 'veld'),('Spel3', 2, 7, 6, 'veld'),('Spel3', 2, 7, 7, 'veld'),('Spel3', 2, 7, 8, 'veld'),('Spel3', 2, 7, 9, 'veld'),
        ('Spel3', 2, 8, 0, 'veld'),('Spel3', 2, 8, 1, 'veld'),('Spel3', 2, 8, 2, 'veld'),('Spel3', 2, 8, 3, 'veld'),('Spel3', 2, 8, 4, 'veld'),('Spel3', 2, 8, 5, 'veld'),('Spel3', 2, 8, 6, 'veld'),('Spel3', 2, 8, 7, 'veld'),('Spel3', 2, 8, 8, 'veld'),('Spel3', 2, 8, 9, 'veld'),
        ('Spel3', 2, 9, 0, 'veld'),('Spel3', 2, 9, 1, 'veld'),('Spel3', 2, 9, 2, 'veld'),('Spel3', 2, 9, 3, 'veld'),('Spel3', 2, 9, 4, 'veld'),('Spel3', 2, 9, 5, 'veld'),('Spel3', 2, 9, 6, 'veld'),('Spel3', 2, 9, 7, 'veld'),('Spel3', 2, 9, 8, 'veld'),('Spel3', 2, 9, 9, 'veld');

    -- Creatie van de Spelstuk per spelbord
    Insert into Spelstuk
    Values
        ('Spel1', 0, 2, 2,'kist'),('Spel1', 0, 4, 3, 'mannetje'),
        ('Spel1', 1, 1, 3, 'kist'),('Spel1', 1, 1, 4, 'mannetje'),
        ('Spel2', 0, 3, 2, 'kist'),('Spel2', 0, 6, 3, 'mannetje'),
        ('Spel2', 1, 5, 2, 'kist'),('Spel2', 1, 4, 8, 'kist'),('Spel2', 1, 3, 3, 'mannetje'),
        ('Spel3', 0, 1, 1, 'kist'),('Spel3', 0, 0, 1, 'mannetje'),
        ('Spel3', 1, 1, 1, 'kist'),('Spel3', 1, 0, 1, 'mannetje'),
        ('Spel3', 2, 1, 1, 'kist'),('Spel3', 2, 0, 1, 'mannetje');

Commit;