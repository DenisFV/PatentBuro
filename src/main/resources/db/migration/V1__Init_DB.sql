create sequence hibernate_sequence start 1 increment 1;

create table request (
  id int8 not null,
  message varchar(2048),
  filename varchar(255),
  ptype varchar(255),
  text varchar(2048),
  user_id int8,
  primary key (id)
);

create table user_role (
  user_id int8 not null,
  roles varchar(255)
);

create table request_checks (
  request_id int8 not null,
  checks varchar(255)
);

create table usr (
  id int8 not null,
  active boolean not null,
  password varchar(255) not null,
  username varchar(255) not null,
  primary key (id)
);

alter table if exists request
  add constraint request_user_fk
  foreign key (user_id) references usr;

alter table if exists user_role
  add constraint user_role_user_fk
  foreign key (user_id) references usr;

alter table if exists request_checks
  add constraint request_checks_request_fk
  foreign key (request_id) references request;