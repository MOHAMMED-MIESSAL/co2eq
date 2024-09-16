-- Enum pour les types de consommation
CREATE TYPE consumption_type AS ENUM ('TRANSPORT', 'LOGEMENT', 'ALIMENTATION');

-- Enum pour les types de véhicules (pour Transport)
CREATE TYPE vehicle_type AS ENUM ('VOITURE', 'TRAIN');

-- Enum pour les types d'énergie (pour Logement)
CREATE TYPE energy_type AS ENUM ('ELECTRICITE', 'GAZ');

-- Enum pour les types d'aliments (pour Alimentation)
CREATE TYPE aliment_type AS ENUM ('VIANDE', 'LEGUME');


-- Table des utilisateurs
CREATE TABLE users (
                       id VARCHAR(50) PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       age INTEGER NOT NULL
);

-- Table des consommations (classe parent)
CREATE TABLE consumptions (
                              id SERIAL PRIMARY KEY,  -- id auto-incrémenté
                              consumption DECIMAL(10, 2) NOT NULL,
                              start_date TIMESTAMP NOT NULL,
                              end_date TIMESTAMP NOT NULL,
                              type consumption_type NOT NULL,  -- Utilisation de l'ENUM consumption_type
                              user_id VARCHAR(50) REFERENCES users(id) ON DELETE CASCADE  -- Lien vers la table users
);

-- Table pour Transport (sous-classe)
CREATE TABLE transport (
                           id INT PRIMARY KEY,  -- Même id que dans la table consumptions
                           distance_parcourue DOUBLE PRECISION NOT NULL,
                           type_de_vehicule vehicle_type NOT NULL,  -- Utilisation de l'ENUM vehicle_type
                           CONSTRAINT fk_transport_consumption
                               FOREIGN KEY (id) REFERENCES consumptions(id) ON DELETE CASCADE  -- Clé étrangère
);

-- Table pour Logement (sous-classe)
CREATE TABLE logement (
                          id INT PRIMARY KEY,  -- Même id que dans la table consumptions
                          consommation_energie DOUBLE PRECISION NOT NULL,
                          type_energie energy_type NOT NULL,  -- Utilisation de l'ENUM energy_type
                          CONSTRAINT fk_logement_consumption
                              FOREIGN KEY (id) REFERENCES consumptions(id) ON DELETE CASCADE  -- Clé étrangère
);

-- Table pour Alimentation (sous-classe)
CREATE TABLE alimentation (
                              id INT PRIMARY KEY,  -- Même id que dans la table consumptions
                              weight DOUBLE PRECISION NOT NULL,
                              aliment_type aliment_type NOT NULL,  -- Utilisation de l'ENUM aliment_type
                              CONSTRAINT fk_alimentation_consumption
                                  FOREIGN KEY (id) REFERENCES consumptions(id) ON DELETE CASCADE  -- Clé étrangère
);

