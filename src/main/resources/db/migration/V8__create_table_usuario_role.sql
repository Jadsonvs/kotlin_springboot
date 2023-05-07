create table usuario_role(
    id bigint not null auto_increment,
    usuario_id BIGINT not null,
    role_id BIGINT not null,
    primary key(id),
    foreign key(usuario_id) references usuario (id),
    foreign key(role_id) references role (id)
);

insert into usuario_role values (1, 1, 1);