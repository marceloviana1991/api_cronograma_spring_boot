alter table avaliacoes add (
cronograma_id bigint not null,
foreign key (cronograma_id) references cronogramas(id)
);