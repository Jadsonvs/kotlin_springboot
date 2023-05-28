insert into usuario (nome, email, password) values ('admin', 'admin@email.com', '$2a$12$hRFxTvdhgLV9dKJfi0j1xeHJ9HRVHvVSwi9M8o8vk3H77gw6p0Z8.');

insert into role (nome) values ('ADMIN');

insert into usuario_role(usuario_id, role_id) values (2, 2);