create table topico(
    id bigint not null auto_increment,
    titulo varchar(50) not null,
    mensagem varchar(300) not null,
    data_criacao datetime not null,
    status varchar(20) not null,
    curso_id bigint not null,
    autor_id bigint not null,
    primary key(id),
    foreign key(curso_id) references curso(id),
    foreign key(autor_id) references usuario(id)
);

insert into topico(id, titulo, mensagem, data_criacao, status, curso_id, autor_id)
values(1, 'Duvida sobre kotlin', 'Minha funcao let nao funciona', '2012-12-24 12:00:00','NAO_RESPONDIDO', 1, 1);