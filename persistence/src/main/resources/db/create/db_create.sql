CREATE USER "pi_kursy" LOGIN ENCRYPTED PASSWORD 'pi_kursy' VALID UNTIL 'infinity';
CREATE DATABASE "pi_kursy" WITH OWNER = "pi_kursy" ENCODING = 'UTF8' CONNECTION LIMIT = 100;

GRANT ALL ON DATABASE "pi_kursy" TO "pi_kursy";

ALTER ROLE "pi_kursy" SET search_path TO "pi_kursy";