create table if not exists customer
(
    id   serial primary key,
    name varchar(255) not null
);

insert into customer (id, name)
values (-1, 'Amanda'),
       (-2, 'Bobby'),
       (-3, 'Cindy'),
       (-4, 'Dennis'),
       (-5, 'Eddie'),
       (-6, 'Fiona'),
       (-7, 'Gordon'),
       (-8, 'Henry'),
       (-9, 'Ivan'),
       (-10, 'Jason'),
       (-11, 'Kurt'),
       (-12, 'Lenny'),
       (-13, 'Mandy'),
       (-14, 'Nancy'),
       (-15, 'Oscar'),
       (-16, 'Penny'),
       (-17, 'Quentin'),
       (-18, 'Randy'),
       (-19, 'Sandy'),
       (-20, 'Tanya'),
       (-21, 'Uma'),
       (-22, 'Vicky'),
       (-23, 'Wendy'),
       (-24, 'Xavier'),
       (-25, 'Yvonne'),
       (-26, 'Zoe');
