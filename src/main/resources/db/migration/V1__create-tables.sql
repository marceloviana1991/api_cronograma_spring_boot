create table cronogramas(

    id bigint not null auto_increment,
    nome varchar(100) not null,

    primary key(id)
);

create table eventos(

    id bigint not null auto_increment,
    nome varchar(100) not null,
    dia_da_semana varchar(100) not null,
    horario time not null,

    primary key(id)
);