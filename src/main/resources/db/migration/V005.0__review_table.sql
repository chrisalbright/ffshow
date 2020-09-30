create table review (
    id int auto_increment,
    movie_id int,
    review varchar(5000),
    email varchar(300),
    name varchar(300),
    stars int,
    reviewed_at date,
    foreign key (movie_id) references movie (id),
    check ( stars between 1 and 5)
)