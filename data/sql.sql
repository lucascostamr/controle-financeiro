CREATE TABLE registro (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    classificacao VARCHAR(255),
    data DATE,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);