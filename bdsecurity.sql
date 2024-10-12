create table usuario (
	id int primary key auto_increment,
	usuario varchar(100) not null,
	password varchar(300) not null,
	email varchar(100) not null
);

create table rol (
	id int primary key auto_increment,
	nombre varchar(100) not null
);

create table usuario_rol (
	idusuario int not null,
	idrol int not null,
	primary key (idusuario, idrol),
	foreign key (idusuario) references usuario(id),
	foreign key (idrol) references rol(id)
);

insert into usuario (usuario, password, email)
values ('pedro', '$2a$12$M/4wpE5qWZABUyC/xnl3auZrjBbqseW9TDAj3SZq8okVh0wJ6VlcO', 'pedro@gmail.com'),
       ('maria', '$2a$12$jTUdEWDZdITR9ozsgIAuueJPjfcUQkVD.YhMKo5WzvIJgi612hMbe', 'maria@gmail.com'),
       ('jose', '$2a$12$Af8R5mqzo/8urwzfzB5fE.4p4cnOW2o422I9vZli2pEPzRxfBUEqG', 'jose@gmail.com');

insert into rol (nombre)
values ('Coordinador'),
       ('Gestor');
       
insert into usuario_rol (idusuario, idrol)
values (1, 1), (2, 2), (3, 1), (3, 2);