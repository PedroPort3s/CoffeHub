-- Atualizações no Banco, nao alterar script de criação, alterar aqui


alter table funcionario 
add senha_funcionario varchar(50) not null;

alter table funcionario add column cod_acesso int;

alter table produto
add un_medida varchar(2) not null;

alter table compra 
change column date_recebido data_recebido date;

alter table compraproduto
modify qtdVenda decimal(18,2)

