USE DWSUCOS;

CREATE TABLE Dim_Organizacional (
                Cod_Organizacional NVARCHAR(50) NOT NULL,
                Cod_Pai NVARCHAR(50) NOT NULL,
                Desc_Organizacional NVARCHAR(250) NOT NULL,
                Direita INT NOT NULL,
                Esquerda INT NOT NULL,
                Nivel INT NOT NULL,
                PRIMARY KEY (Cod_Organizacional)
);


CREATE TABLE Dim_Categoria (
                Cod_Categoria NVARCHAR(50) NOT NULL,
                Desc_Categoria NVARCHAR(250) NOT NULL,
                PRIMARY KEY (Cod_Categoria)
);


CREATE TABLE Dim_Marca (
                Cod_Marca NVARCHAR(50) NOT NULL,
                Cod_Categoria NVARCHAR(50) NOT NULL,
                Desc_Marca NVARCHAR(250) NOT NULL,
                PRIMARY KEY (Cod_Marca)
);


CREATE TABLE Dim_Produto (
                Cod_Produto NVARCHAR(50) NOT NULL,
                Cod_Marca NVARCHAR(50) NOT NULL,
                Desc_Produto NVARCHAR(250) NOT NULL,
                Atr_Tamanho NVARCHAR(250) NOT NULL,
                Atr_Sabor NVARCHAR(250) NOT NULL,
                PRIMARY KEY (Cod_Produto)
);


CREATE TABLE Dim_Tempo (
                Cod_Tempo NVARCHAR(50) NOT NULL,
                Data DATE NOT NULL,
                Numero_Dia_Semana NVARCHAR(50) NOT NULL,
                Numero_Mes NVARCHAR(50) NOT NULL,
                Nome_Mes NVARCHAR(250) NOT NULL,
                Numero_Trimestre NVARCHAR(50) NOT NULL,
                Nome_Trimestre NVARCHAR(250) NOT NULL,
                Numero_Semestre NVARCHAR(50) NOT NULL,
                Nome_Semestre NVARCHAR(250) NOT NULL,
                Numero_Ano NVARCHAR(50) NOT NULL,
                PRIMARY KEY (Cod_Tempo)
);


CREATE TABLE Dim_Cliente (
                Cod_Cliente NVARCHAR(50) NOT NULL,
                Desc_Cliente NVARCHAR(250) NOT NULL,
                Cod_Cidade NVARCHAR(50) NOT NULL,
                Desc_Cidade NVARCHAR(250) NOT NULL,
                Cod_Estado NVARCHAR(50) NOT NULL,
                Desc_Estado NVARCHAR(250) NOT NULL,
                Cod_Regiao NVARCHAR(50) NOT NULL,
                Desc_Regiao NVARCHAR(250) NOT NULL,
                Cod_Segmento NVARCHAR(50) NOT NULL,
                Desc_Segmento NVARCHAR(250) NOT NULL,
                PRIMARY KEY (Cod_Cliente)
);


CREATE TABLE Fato_004 (
                Cod_Tempo NVARCHAR(50) NOT NULL,
                Cod_Cliente NVARCHAR(50) NOT NULL,
                Cod_Organizacional NVARCHAR(50) NOT NULL,
                Cod_Produto NVARCHAR(50) NOT NULL,
                Meta_Faturamento DOUBLE PRECISION NOT NULL,
                PRIMARY KEY (Cod_Tempo, Cod_Cliente, Cod_Organizacional, Cod_Produto)
);


CREATE TABLE Dim_Fabrica (
                Cod_Fabrica NVARCHAR(50) NOT NULL,
                Desc_Fabrica NVARCHAR(250) NOT NULL,
                PRIMARY KEY (Cod_Fabrica)
);

ALTER TABLE Dim_Fabrica COMMENT 'Tabela de dimensão Fábrica';


CREATE TABLE Fato_005 (
                Cod_Tempo NVARCHAR(50) NOT NULL,
                Cod_Fabrica NVARCHAR(50) NOT NULL,
                Cod_Produto NVARCHAR(50) NOT NULL,
                Meta_Custo DOUBLE PRECISION NOT NULL,
                PRIMARY KEY (Cod_Tempo, Cod_Fabrica, Cod_Produto)
);


CREATE TABLE Fato_003 (
                Cod_Fabrica NVARCHAR(50) NOT NULL,
                Cod_Tempo NVARCHAR(50) NOT NULL,
                Custo_Fixo DOUBLE PRECISION NOT NULL,
                PRIMARY KEY (Cod_Fabrica, Cod_Tempo)
);


CREATE TABLE Fato_002 (
                Cod_Fabrica NVARCHAR(50) NOT NULL,
                Cod_Tempo NVARCHAR(50) NOT NULL,
                Cod_Cliente NVARCHAR(50) NOT NULL,
                Cod_Produto NVARCHAR(50) NOT NULL,
                Custo_Frete DOUBLE PRECISION NOT NULL,
                PRIMARY KEY (Cod_Fabrica, Cod_Tempo, Cod_Cliente, Cod_Produto)
);


CREATE TABLE Fato_001 (
                Cod_Fabrica NVARCHAR(50) NOT NULL,
                Cod_Tempo NVARCHAR(50) NOT NULL,
                Cod_Cliente NVARCHAR(50) NOT NULL,
                Cod_Organizacional NVARCHAR(50) NOT NULL,
                Cod_Produto NVARCHAR(50) NOT NULL,
                Faturamento DOUBLE PRECISION NOT NULL,
                Quantidade_Vendida DOUBLE PRECISION NOT NULL,
                Imposto DOUBLE PRECISION NOT NULL,
                Custo_Variavel DOUBLE PRECISION NOT NULL,
                PRIMARY KEY (Cod_Fabrica, Cod_Tempo, Cod_Cliente, Cod_Organizacional, Cod_Produto)
);


ALTER TABLE Fato_001 ADD CONSTRAINT dim_organizacional_fato_001_fk
FOREIGN KEY (Cod_Organizacional)
REFERENCES Dim_Organizacional (Cod_Organizacional)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_004 ADD CONSTRAINT dim_organizacional_fato_004_fk
FOREIGN KEY (Cod_Organizacional)
REFERENCES Dim_Organizacional (Cod_Organizacional)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Dim_Marca ADD CONSTRAINT dim_categoria_dim_marca_fk
FOREIGN KEY (Cod_Categoria)
REFERENCES Dim_Categoria (Cod_Categoria)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Dim_Produto ADD CONSTRAINT dim_marca_dim_produto_fk
FOREIGN KEY (Cod_Marca)
REFERENCES Dim_Marca (Cod_Marca)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_001 ADD CONSTRAINT dim_produto_fato_001_fk
FOREIGN KEY (Cod_Produto)
REFERENCES Dim_Produto (Cod_Produto)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_002 ADD CONSTRAINT dim_produto_fato_002_fk
FOREIGN KEY (Cod_Produto)
REFERENCES Dim_Produto (Cod_Produto)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_004 ADD CONSTRAINT dim_produto_fato_004_fk
FOREIGN KEY (Cod_Produto)
REFERENCES Dim_Produto (Cod_Produto)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_005 ADD CONSTRAINT dim_produto_fato_005_fk
FOREIGN KEY (Cod_Produto)
REFERENCES Dim_Produto (Cod_Produto)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_001 ADD CONSTRAINT dim_tempo_fato_001_fk
FOREIGN KEY (Cod_Tempo)
REFERENCES Dim_Tempo (Cod_Tempo)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_002 ADD CONSTRAINT dim_tempo_fato_002_fk
FOREIGN KEY (Cod_Tempo)
REFERENCES Dim_Tempo (Cod_Tempo)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_003 ADD CONSTRAINT dim_tempo_fato_003_fk
FOREIGN KEY (Cod_Tempo)
REFERENCES Dim_Tempo (Cod_Tempo)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_004 ADD CONSTRAINT dim_tempo_fato_004_fk
FOREIGN KEY (Cod_Tempo)
REFERENCES Dim_Tempo (Cod_Tempo)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_005 ADD CONSTRAINT dim_tempo_fato_005_fk
FOREIGN KEY (Cod_Tempo)
REFERENCES Dim_Tempo (Cod_Tempo)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_001 ADD CONSTRAINT dim_cliente_fato_001_fk
FOREIGN KEY (Cod_Cliente)
REFERENCES Dim_Cliente (Cod_Cliente)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_002 ADD CONSTRAINT dim_cliente_fato_002_fk
FOREIGN KEY (Cod_Cliente)
REFERENCES Dim_Cliente (Cod_Cliente)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_004 ADD CONSTRAINT dim_cliente_fato_004_fk
FOREIGN KEY (Cod_Cliente)
REFERENCES Dim_Cliente (Cod_Cliente)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_001 ADD CONSTRAINT dim_fabrica_fato_001_fk
FOREIGN KEY (Cod_Fabrica)
REFERENCES Dim_Fabrica (Cod_Fabrica)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_002 ADD CONSTRAINT dim_fabrica_fato_002_fk
FOREIGN KEY (Cod_Fabrica)
REFERENCES Dim_Fabrica (Cod_Fabrica)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_003 ADD CONSTRAINT dim_fabrica_fato_003_fk
FOREIGN KEY (Cod_Fabrica)
REFERENCES Dim_Fabrica (Cod_Fabrica)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Fato_005 ADD CONSTRAINT dim_fabrica_fato_005_fk
FOREIGN KEY (Cod_Fabrica)
REFERENCES Dim_Fabrica (Cod_Fabrica)
ON DELETE NO ACTION
ON UPDATE NO ACTION;