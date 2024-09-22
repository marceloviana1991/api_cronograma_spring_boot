create table avaliacoes(

    id bigint not null auto_increment,
    nota double not null,
    texto varchar(250) not null,
    data date not null,
    evento_id bigint not null,
    foreign key (evento_id) references eventos(id),

    primary key(id)
);