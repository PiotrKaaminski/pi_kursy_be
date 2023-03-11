insert into users(external_id, username, password, role, status)
values
-- user with login "admin" and password "temp", requires changing password on first login
('1bb60d65-056f-49bd-a483-a2f0be1a4b2b', 'admin', '$2a$12$jCQKI4oBIgGFGX9QcfokLOjASHexl33FG8En6VkjydDgkCrs61aeW', 'ADMIN', 'PENDING');