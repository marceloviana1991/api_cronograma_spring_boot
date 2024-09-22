create table cronogramas(

    id bigint not null auto_increment,
    nome varchar(100) not null,

    primary key(id)
);

create table eventos(

    id bigint not null auto_increment,
    nome varchar(100) not null,
    dia_da_semana integer not null,
    horario time not null,
    horario_termina time not null,
    cronograma_id bigint not null,
    foreign key (cronograma_id) references cronogramas(id),

    primary key(id)
);