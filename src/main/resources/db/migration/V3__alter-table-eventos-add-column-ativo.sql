alter table eventos add ativo tinyint;
update eventos set ativo = 1;