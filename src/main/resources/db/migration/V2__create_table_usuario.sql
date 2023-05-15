create table usuario(
    id bigint not null auto_increment,
    nome varchar(50) not null,
    email varchar(50) not null,
    password varchar(100) not null,
    primary key(id)
);
insert into usuario values(1, 'Jadson Viana', 'jadson@email.com', '$2a$12$hRFxTvdhgLV9dKJfi0j1xeHJ9HRVHvVSwi9M8o8vk3H77gw6p0Z8.');