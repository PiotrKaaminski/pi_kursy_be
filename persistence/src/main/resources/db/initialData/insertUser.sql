insert into users(id, username, password, role, creation_date)
values
-- user with login "admin" and password "temp", requires changing password on first login
('1bb60d65-056f-49bd-a483-a2f0be1a4b2b', 'admin', '$2a$12$WTSArclcdErD/lwMXbUW..WpK5OizSbghDW481dw5oH8yfb8KP4DG', 'ADMIN', current_timestamp(0));