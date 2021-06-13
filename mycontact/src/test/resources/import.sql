insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`, `job`) values (1, 'Justin', 10, 'A', 1990, 5, 1, 'Programmer');
insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (2, 'David', 10, 'B', 1990, 4, 1);
insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (3, 'Dennis', 10, 'O', 1990, 3, 1);
insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (4, 'Sophia', 10, 'AB', 1990, 2, 1);
insert into person(`id`, `name`, `age`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (5, 'Benny', 10, 'A', 1993, 1, 1);

insert into block(`id`, `name`) values (1, 'Dennis');
insert into block(`id`, `name`) values (2, 'Sophia');

update person set block_id = 1 where id = 3;
update person set block_id = 2 where id = 4;