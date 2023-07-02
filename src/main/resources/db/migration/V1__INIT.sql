create table admin
(
    user_id  bigint       not null,
    inn      varchar(255) not null,
    org_name varchar(255) not null,
    primary key (user_id)
)
create table contract
(
    number   serial    not null,
    status   smallint  not null check (status between 0 and 1),
    admin_id bigint    not null unique,
    id       bigserial not null,
    primary key (id)
)
create table event
(
    cost     integer      not null,
    admin_id bigint       not null,
    id       bigserial    not null,
    name     varchar(255) not null,
    primary key (id)
)
create table event_participant
(
    event_id       bigint not null,
    participant_id bigint not null
)
create table participant
(
    age            integer      not null,
    has_covid_test boolean      not null,
    user_id        bigint       not null,
    father_name    varchar(255) not null,
    first_name     varchar(255) not null,
    last_name      varchar(255) not null,
    primary key (user_id)
)
create table role
(
    id   bigserial    not null,
    name varchar(255) not null unique check (name in ('ADMIN', 'PARTICIPANT')),
    primary key (id)
)
create table user_roles
(
    role_id bigint not null,
    user_id bigint not null,
    primary key (role_id, user_id)
)
create table users
(
    id       bigserial    not null,
    login    varchar(255) not null unique,
    password varchar(255) not null,
    primary key (id)
)
alter table if exists admin add constraint admin_users_fk foreign key (user_id) references users
alter table if exists contract add constraint contract_admin_fk foreign key (admin_id) references admin
alter table if exists event add constraint event_admin_fk foreign key (admin_id) references admin
alter table if exists event_participant add constraint event_participant_participant_fk foreign key (participant_id) references participant
alter table if exists event_participant add constraint event_participant_event_fk foreign key (event_id) references event
alter table if exists participant add constraint participant_users_fk foreign key (user_id) references users
alter table if exists user_roles add constraint user_roles_role_fk foreign key (role_id) references role
alter table if exists user_roles add constraint user_roles_users_fk foreign key (user_id) references users