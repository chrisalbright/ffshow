alter table show_time
    add constraint theater_ref FOREIGN KEY (theater_id) REFERENCES theater (id)
