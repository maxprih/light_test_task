CREATE TABLE DinoPark (
                          id SERIAL PRIMARY KEY,
                          name TEXT NOT NULL,
                          location TEXT NOT NULL,
                          description TEXT NOT NULL
);

CREATE TABLE DinosaurSpecies (
                                 id SERIAL PRIMARY KEY,
                                 species_name TEXT UNIQUE NOT NULL
);

CREATE TABLE Dinosaur (
                          id SERIAL PRIMARY KEY,
                          name TEXT NOT NULL,
                          species_id INTEGER REFERENCES DinosaurSpecies(id) NOT NULL,
                          dinopark_id INTEGER REFERENCES DinoPark(id) NOT NULL,
                          gender TEXT NOT NULL
);


CREATE TABLE Laboratory (
                            id SERIAL PRIMARY KEY,
                            name TEXT NOT NULL,
                            location TEXT NOT NULL,
                            description TEXT NOT NULL
);

CREATE TABLE Researcher (
                            id SERIAL PRIMARY KEY,
                            name TEXT NOT NULL,
                            laboratory_id INTEGER REFERENCES Laboratory(id) NOT NULL,
                            specialization TEXT NOT NULL
);

CREATE TABLE GeneticControl (
                                id SERIAL PRIMARY KEY,
                                dinosaur_id INTEGER REFERENCES Dinosaur(id) NOT NULL,
                                laboratory_id INTEGER REFERENCES Laboratory(id) NOT NULL,
                                test_results TEXT NOT NULL,
                                test_date DATE NOT NULL
);

CREATE TABLE Report (
                        id SERIAL PRIMARY KEY,
                        laboratory_id INTEGER REFERENCES Laboratory(id) NOT NULL,
                        report_date DATE NOT NULL,
                        report_text TEXT NOT NULL
);

CREATE TABLE ReportsResearchers (
                                    report_id INTEGER REFERENCES Report(id) NOT NULL,
                                    researcher_id INTEGER REFERENCES Researcher(id) NOT NULL,
                                    PRIMARY KEY (report_id, researcher_id)
);