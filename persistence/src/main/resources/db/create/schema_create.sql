CREATE SCHEMA "pi_kursy";
ALTER SCHEMA "pi_kursy" OWNER TO "pi_kursy";

REVOKE ALL ON DATABASE "pi_kursy" FROM public;
REVOKE ALL ON SCHEMA "pi_kursy" FROM public;

GRANT CONNECT ON DATABASE "pi_kursy" TO "pi_kursy";
GRANT USAGE ON SCHEMA "pi_kursy" TO "pi_kursy";
