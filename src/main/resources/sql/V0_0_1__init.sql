

create table app_user (
                      id char(16) not null primary key,
                      creation_datetime timestamp not null default CURRENT_TIMESTAMP
);

create table daily_answer(
                             id char(16) not null primary key,
                             app_user_id char(16) not null,
                             fiebre varchar(191),
                             creation_datetime timestamp not null default CURRENT_TIMESTAMP
);

create table pathologies(
    app_user_id char(16) not null,
    pathology varchar(191),
    primary key (app_user_id, pathology),
    unique (app_user_id, pathology)
);

alter table pathologies
    add constraint pathologies_user_fk
        foreign key(app_user_id)
        references app_user(id)
        on delete cascade;

alter table daily_answer
    add constraint daily_answer_user_fk
        foreign key (app_user_id)
            references app_user(id)
            on delete cascade;