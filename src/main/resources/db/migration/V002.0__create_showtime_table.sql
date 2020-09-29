create table show_time (
  id INT AUTO_INCREMENT,
  movie_id INT,
  ticket_price DECIMAL,
  start_time TIME,
  FOREIGN KEY (movie_id) REFERENCES movie (id)
)