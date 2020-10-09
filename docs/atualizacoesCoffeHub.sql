-- Atualizações no Banco, nao alterar script de criação, alterar aqui


alter table funcionario 
add senha_funcionario varchar(50) not null;

alter table produto
add un_medida varchar(2) not null; 