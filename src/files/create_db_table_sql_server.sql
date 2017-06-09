/**
 * CREATE DATABASE AND TABLES FOR SQL SERVER
 */


CREATE DATABASE AG

   CREATE TABLE FUNCIONARIO
   (
       CODIGO INT NOT NULL PRIMARY KEY,
       NOME VARCHAR(255),
       CARGO INT ,
       TEMPO_EXP FLOAT,
       TEMPO_PROJ FLOAT,
       AREA INT
	   
	   CONSTRAINT FK_FUNC_AREA FOREIGN KEY (AREA) REFERENCES AREA(CODIGO),
	   CONSTRAINT FK_FUNC_CARGO FOREIGN KEY (CARGO) REFERENCES CARGO(CODIGO)
   )
   
  CREATE TABLE CARGO
  (
      CODIGO INT PRIMARY KEY,
      DESCRICAO VARCHAR(255)
  )
  
  CREATE TABLE AREA
  (
      CODIGO INT PRIMARY KEY,
      DESCRICAO VARCHAR(255)
  )
  
  CREATE TABLE ATIVIDADE
  (
      CODIGO INT PRIMARY KEY,
      DESCRICAO VARCHAR(255),
      PRAZO FLOAT,
      FUNCIONARIO  INT REFERENCES FUNCIONARIO (CODIGO)      
  )
  
  select * from cargo
  
  insert into cargo values(1,'Programador Jr.')
  insert into cargo values(2,'Programador Pleno')
  insert into cargo values(3,'Analista Jr.')
  insert into cargo values(4,'Analista Pleno')
  insert into cargo values(5,'Analista Sênior')
  insert into area values(1,'Desenvolvimento')
  
  select * from area
  insert into area values(1,'Desenvolvimento')
  insert into area values(2,'Análise de Software')
  insert into area values(3,'Análise de Requisítos')
  insert into area values(4,'Banco de Dados')
  insert into area values(5,'Teste')
  
  select * from funcionario
  
  insert into funcionario values( 1, 'Celso Oliveira de Souza', 2, 2.5, 1.0,1)