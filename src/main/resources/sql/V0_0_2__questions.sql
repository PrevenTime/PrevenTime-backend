

alter table app_user
    add column postal_code varchar(191),
    add column birth_date date;

alter table daily_answer drop column fiebre;

alter table daily_answer
    add column answer_date date,
    add column dry_cough varchar(191),
    add column fever varchar(191),
    add column fever_amount varchar(191),
    add column difficulty_breathing varchar(191),
    add column diarrhea varchar(191);
