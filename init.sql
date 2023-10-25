CREATE TABLE IF NOT EXISTS pessoa
(
    apelido character varying(32) COLLATE pg_catalog."default" NOT NULL,
    nome character varying(100) COLLATE pg_catalog."default" NOT NULL,
    stack text[] COLLATE pg_catalog."default",
    nascimento character varying(10) COLLATE pg_catalog."default" NOT NULL,
    id uuid NOT NULL,
    termo character varying(500) COLLATE pg_catalog."default" GENERATED ALWAYS AS ((((((((apelido)::text || ' '::text) || (nome)::text) || ' '::text) || (nascimento)::text) || ' '::text) || stack)) STORED,
    CONSTRAINT apelido_unq UNIQUE (apelido)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS pessoa
    OWNER to ggrun;