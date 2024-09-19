-- Script SQL para Apache H2 que cria as tabelas e popula o banco.
drop table if exists cidade;
drop table if exists estado;
drop table if exists regiao_geografica;

CREATE TABLE regiao_geografica
(
    id   bigint auto_increment PRIMARY KEY NOT NULL,
    nome varchar(75)        NOT NULL
);

CREATE UNIQUE INDEX ix_regiao ON regiao_geografica (nome);

CREATE TABLE estado
(
    id        bigint auto_increment PRIMARY KEY NOT NULL,
    nome      varchar(75)        NOT NULL,
    uf        varchar(2)         NOT NULL,
    regiao_id int                NOT NULL,
    area_km2  int                NOT NULL default 0,
    populacao int                NOT NULL default 0,
    constraint fk_estado_regiao foreign key (regiao_id) references regiao_geografica (id)
);

CREATE UNIQUE INDEX ix_estado ON estado (nome);
CREATE UNIQUE INDEX ix_uf ON estado (uf);

CREATE TABLE cidade
(
    id        bigint auto_increment PRIMARY KEY NOT NULL,
    nome      varchar(120)       NOT NULL,
    estado_id int                NOT NULL,
    constraint fk_cidade_estado foreign key (estado_id) references estado (id)
);

CREATE UNIQUE INDEX ix_cidade ON cidade (nome, estado_id);

-- ########################################################################################################

INSERT INTO regiao_geografica (nome)
VALUES ('Norte'),
       ('Nordeste'),
       ('Centro-Oeste'),
       ('Sudeste'),
       ('Sul');

INSERT INTO estado (nome, uf, regiao_id, area_km2, populacao)
VALUES ('Acre', 'AC', 1, 164123, 829780),
       ('Alagoas', 'AL', 2, 27848, 3125254),
       ('Amazonas', 'AM', 1, 1559167, 3952262),
       ('Amapá', 'AP', 1, 142470, 774268),
       ('Bahia', 'BA', 2, 564760, 14659023),
       ('Ceará', 'CE', 2, 148894, 8936431),
       ('Distrito Federal', 'DF', 3, 5760, 2923369),
       ('Espírito Santo', 'ES', 4, 46074, 4108508),
       ('Goiás', 'GO', 3, 340203, 6950976),
       ('Maranhão', 'MA', 2, 329642, 6800605),
       ('Minas Gerais', 'MG', 4, 586521, 20732660),
       ('Mato Grosso do Sul', 'MS', 3, 357145, 2833742),
       ('Mato Grosso', 'MT', 3, 903207, 3784239),
       ('Pará', 'PA', 1, 1245870, 8442962),
       ('Paraíba', 'PB', 2, 56467, 4030961),
       ('Pernambuco', 'PE', 2, 98067, 9051113),
       ('Piauí', 'PI', 2, 251756, 3270174),
       ('Paraná', 'PR', 5, 199298, 11835379),
       ('Rio de Janeiro', 'RJ', 4, 43750, 16615526),
       ('Rio Grande do Norte', 'RN', 2, 52809, 3303953),
       ('Rondônia', 'RO', 1, 237765, 1616379),
       ('Roraima', 'RR', 1, 223644, 634805),
       ('Rio Grande do Sul', 'RS', 5, 281707, 11088065),
       ('Santa Catarina', 'SC', 5, 95730, 7762154),
       ('Sergipe', 'SE', 2, 21925, 2211868),
       ('São Paulo', 'SP', 4, 248219, 46024937),
       ('Tocantins', 'TO', 1, 277466, 1584306);

INSERT INTO cidade (nome, estado_id)
VALUES ('Afonso Cláudio', 8),
       ('Água Doce do Norte', 8),
       ('Águia Branca', 8),
       ('Alegre', 8),
       ('Alfredo Chaves', 8),
       ('Alto Rio Novo', 8),
       ('Anchieta', 8),
       ('Apiacá', 8),
       ('Aracruz', 8),
       ('Atilio Vivacqua', 8);

