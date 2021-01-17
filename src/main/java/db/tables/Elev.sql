CREATE TABLE IF NOT EXISTS Elev (
    idnp_elev BIGINT PRIMARY KEY,
    nume NVARCHAR(50) NOT NULL,
    prenume NVARCHAR(50) NOT NULL,
    id_grupa NVARCHAR(10),

    CONSTRAINT fk_id_grupa FOREIGN KEY (id_grupa) REFERENCES Grupe(nume_grupa)
);