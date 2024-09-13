create table transacao
(
    id      serial         not null primary key,
    cliente varchar(120)   not null,
    valor   decimal(10, 2) not null,
    moeda   varchar(3)     not null,
    tipo    char           not null
);


create table aluno
(
    matricula serial       not null primary key,
    nome      varchar(120) not null,
    nota1     decimal(10, 2),
    nota2     decimal(10, 2),
    nota3     decimal(10, 2)
);

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
    capital   boolean            not null default false,
    constraint fk_cidade_estado foreign key (estado_id) references estado (id)
);

CREATE UNIQUE INDEX ix_cidade ON cidade (nome, estado_id);

create table cliente
(
    id              bigint auto_increment PRIMARY KEY not null,
    nome            varchar(75)        not null,
    cpf             varchar(11)        not null,
    cidade_id       int                not null,
    data_nascimento date               not null,
    constraint fk_cliente_cidade foreign key (cidade_id) references cidade (id)
);

create unique INDEX ix_cpf_cliente on cliente (cpf);

create table loja
(
    id               bigint auto_increment PRIMARY KEY not null,
    cidade_id        int                not null,
    data_inauguracao date               not null,
    constraint fk_loja_cidade foreign key (cidade_id) references cidade (id)
);


create table funcionario
(
    id              bigint auto_increment PRIMARY KEY not null,
    nome            varchar(75)        not null,
    cpf             varchar(11)        not null,
    loja_id         int                not null,
    data_nascimento date               not null,
    constraint fk_funcionario_loja foreign key (loja_id) references loja (id)
);

create unique INDEX ix_cpf_funcionario on funcionario (cpf);

create table marca
(
    id   bigint auto_increment PRIMARY KEY not null,
    nome varchar(200)       not null
);

create unique INDEX ix_marca on marca (nome);

create table produto
(
    id       bigint auto_increment PRIMARY KEY not null,
    nome     varchar(200)       not null,
    marca_id int                not null,
    valor    decimal(10, 2)     not null,
    constraint fk_produto_marca foreign key (marca_id) references marca (id)
);

create table estoque
(
    produto_id int not null,
    loja_id    int not null,
    quant      int not null,
    primary key (produto_id, loja_id),
    constraint fk_estoque_produto foreign key (produto_id) references produto (id) on delete cascade,
    constraint fk_estoque_loja foreign key (loja_id) references loja (id)
);

create table venda
(
    id             bigint auto_increment PRIMARY KEY not null,
    loja_id        int                not null,
    cliente_id     int                not null,
    funcionario_id int                not null,
    data_cadastro  timestamp          not null default current_timestamp,
    constraint fk_venda_loja foreign key (loja_id) references loja (id),
    constraint fk_venda_cliente foreign key (cliente_id) references cliente (id),
    constraint fk_venda_funcionario foreign key (funcionario_id) references funcionario (id)
);

create table item_venda
(
    venda_id   int            not null,
    produto_id int            not null,
    quant      int            not null,
    valor      decimal(10, 2) not null,
    primary key (venda_id, produto_id),
    constraint fk_itemvenda_venda foreign key (venda_id) references venda (id) on delete cascade,
    constraint fk_itemvenda_produto foreign key (produto_id) references produto (id)
);

-- ########################################################################################################

INSERT INTO regiao_geografica (nome)
VALUES ('Norte'),
       ('Nordeste'),
       ('Centro-Oeste'),
       ('Sudeste'),
       ('Sul');

INSERT INTO estado (id, nome, uf, regiao_id, area_km2, populacao)
VALUES (1, 'Acre', 'AC', 1, 164123, 829780),
       (2, 'Alagoas', 'AL', 2, 27848, 3125254),
       (3, 'Amazonas', 'AM', 1, 1559167, 3952262),
       (4, 'Amapá', 'AP', 1, 142470, 774268),
       (5, 'Bahia', 'BA', 2, 564760, 14659023),
       (6, 'Ceará', 'CE', 2, 148894, 8936431),
       (7, 'Distrito Federal', 'DF', 3, 5760, 2923369),
       (8, 'Espírito Santo', 'ES', 4, 46074, 4108508),
       (9, 'Goiás', 'GO', 3, 340203, 6950976),
       (10, 'Maranhão', 'MA', 2, 329642, 6800605),
       (11, 'Minas Gerais', 'MG', 4, 586521, 20732660),
       (12, 'Mato Grosso do Sul', 'MS', 3, 357145, 2833742),
       (13, 'Mato Grosso', 'MT', 3, 903207, 3784239),
       (14, 'Pará', 'PA', 1, 1245870, 8442962),
       (15, 'Paraíba', 'PB', 2, 56467, 4030961),
       (16, 'Pernambuco', 'PE', 2, 98067, 9051113),
       (17, 'Piauí', 'PI', 2, 251756, 3270174),
       (18, 'Paraná', 'PR', 5, 199298, 11835379),
       (19, 'Rio de Janeiro', 'RJ', 4, 43750, 16615526),
       (20, 'Rio Grande do Norte', 'RN', 2, 52809, 3303953),
       (21, 'Rondônia', 'RO', 1, 237765, 1616379),
       (22, 'Roraima', 'RR', 1, 223644, 634805),
       (23, 'Rio Grande do Sul', 'RS', 5, 281707, 11088065),
       (24, 'Santa Catarina', 'SC', 5, 95730, 7762154),
       (25, 'Sergipe', 'SE', 2, 21925, 2211868),
       (26, 'São Paulo', 'SP', 4, 248219, 46024937),
       (27, 'Tocantins', 'TO', 1, 277466, 1584306);

INSERT INTO cidade (id, nome, estado_id)
VALUES (1, 'Afonso Cláudio', 8),
       (2, 'Água Doce do Norte', 8),
       (3, 'Águia Branca', 8),
       (4, 'Alegre', 8),
       (5, 'Alfredo Chaves', 8),
       (6, 'Alto Rio Novo', 8),
       (7, 'Anchieta', 8),
       (8, 'Apiacá', 8),
       (9, 'Aracruz', 8),
       (10, 'Atilio Vivacqua', 8);

-- ###################################################

INSERT INTO cliente (nome, cpf, cidade_id, data_nascimento) VALUES ('Wendy Parker', '96557866635', 1, '1994-04-16');
INSERT INTO cliente (nome, cpf, cidade_id, data_nascimento) VALUES ('Stephanie Frami', '19485406486', 2, '1968-10-01');
INSERT INTO cliente (nome, cpf, cidade_id, data_nascimento) VALUES ('Stewart Lesch', '88344327169', 2, '1946-05-09');
INSERT INTO cliente (nome, cpf, cidade_id, data_nascimento) VALUES ('Mr. Victor Langosh', '16057371750', 3, '2005-11-20');
INSERT INTO cliente (nome, cpf, cidade_id, data_nascimento) VALUES ('Dr. Elijah Welch', '42006254770', 3, '1967-05-27');
INSERT INTO cliente (nome, cpf, cidade_id, data_nascimento) VALUES ('Suzanne Kreiger', '23828465229', 1, '1993-11-04');
INSERT INTO cliente (nome, cpf, cidade_id, data_nascimento) VALUES ('Phillip Larkin', '92109749005', 4, '1955-10-19');

INSERT INTO loja (cidade_id, data_inauguracao) VALUES (1, '1943-05-11');
INSERT INTO loja (cidade_id, data_inauguracao) VALUES (1, '1956-12-03');
INSERT INTO loja (cidade_id, data_inauguracao) VALUES (2, '1981-05-24');
INSERT INTO loja (cidade_id, data_inauguracao) VALUES (3, '1984-04-23');

INSERT INTO funcionario (nome, cpf, loja_id, data_nascimento) VALUES ('Jessie Jacobs', '49075676852', 1, '1959-02-02');
INSERT INTO funcionario (nome, cpf, loja_id, data_nascimento) VALUES ('Santiago Mante', '32914553214', 1, '1982-10-27');
INSERT INTO funcionario (nome, cpf, loja_id, data_nascimento) VALUES ('Esther Sauer', '16900316437', 2, '2001-10-11');
INSERT INTO funcionario (nome, cpf, loja_id, data_nascimento) VALUES ('Dawn Huel', '42142795296', 2, '1989-02-17');

INSERT INTO marca (nome) VALUES ('Little, Boyle and Abshire');
INSERT INTO marca (nome) VALUES ('Rempel - Herman');
INSERT INTO marca (nome) VALUES ('Senger - Grimes');
INSERT INTO marca (nome) VALUES ('Pouros - Gutmann');
INSERT INTO marca (nome) VALUES ('Ebert Inc');

INSERT INTO produto (nome, marca_id, valor) VALUES ('Recycled Rubber Shirt', 5, 102);
INSERT INTO produto (nome, marca_id, valor) VALUES ('Recycled Frozen Shirt', 1, 798);
INSERT INTO produto (nome, marca_id, valor) VALUES ('Modern Bronze Cheese', 3, 945);
INSERT INTO produto (nome, marca_id, valor) VALUES ('Modern Soft Sausages', 3, 786);
INSERT INTO produto (nome, marca_id, valor) VALUES ('Fantastic Granite Bacon', 4, 23);
INSERT INTO produto (nome, marca_id, valor) VALUES ('Handcrafted Rubber Chips', 1, 910);
INSERT INTO produto (nome, marca_id, valor) VALUES ('Awesome Metal Gloves', 5, 907);
INSERT INTO produto (nome, marca_id, valor) VALUES ('Sleek Granite Shirt', 2, 319);
INSERT INTO produto (nome, marca_id, valor) VALUES ('Electronic Fresh Soap', 5, 971);
INSERT INTO produto (nome, marca_id, valor) VALUES ('Modern Concrete Cheese', 5, 308);
