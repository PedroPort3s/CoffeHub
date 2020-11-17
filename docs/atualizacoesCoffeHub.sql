-- Atualizações no Banco, nao alterar script de criação, alterar aqui

alter table funcionario 
add senha_funcionario varchar(50) not null;

alter table funcionario add column cod_acesso int;

alter table produto
add un_medida varchar(2) not null;

alter table compra 
change column date_recebido data_recebido date;

alter table compraproduto
modify qtdVenda decimal(18,2);

alter table compraproduto 
drop primary key;

alter table compraproduto 
add primary key (num_item, cod_Compra, cod_Produto);

alter table vendaitem 
drop primary key;

alter table vendaitem 
add primary key (num_item, cod_venda, cod_Produto);

create table UnidadeMedida(
	id int primary key,
	codUnidade varchar(2) unique,
    nome varchar(50),
    permiteFracionada varchar(1)
);

alter table Produto drop column un_medida;
alter table Produto add column idUnidadeMedida int;
alter table Produto add foreign key (codUnidadeMedida) references UnidadeMedida(id);

select * from Produto


