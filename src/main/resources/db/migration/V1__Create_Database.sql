CREATE TABLE
	c_security_permission ( id CHARACTER VARYING(255) NOT NULL,
	permission_label CHARACTER VARYING(255) NOT NULL,
	permission_value CHARACTER VARYING(255) NOT NULL,
	CONSTRAINT c_security_permission_pk PRIMARY KEY (id) );

CREATE TABLE
	c_security_role ( id CHARACTER VARYING(255) NOT NULL,
	description CHARACTER VARYING(255),
	name CHARACTER VARYING(255) NOT NULL,
	CONSTRAINT c_security_role_pk PRIMARY KEY (id) );

CREATE TABLE
	c_security_role_permission ( id BIGSERIAL NOT NULL,
	id_role CHARACTER VARYING(255) NOT NULL,
	id_permission CHARACTER VARYING(255) NOT NULL,
	CONSTRAINT c_security_role_permission_pk PRIMARY KEY (id) );

CREATE TABLE
	c_security_user ( id CHARACTER VARYING(255) NOT NULL,
	active BOOLEAN NOT NULL,
	username CHARACTER VARYING(255) NOT NULL,
	id_role CHARACTER VARYING(255) NOT NULL,
	CONSTRAINT c_security_user_pk PRIMARY KEY (id) );

CREATE TABLE
	c_security_user_password ( id BIGSERIAL NOT NULL,
	id_user CHARACTER VARYING(36) NOT NULL,
	password CHARACTER VARYING(255) NOT NULL,
	CONSTRAINT c_security_user_password_pk PRIMARY KEY (id) );

CREATE TABLE
	site_users ( id BIGSERIAL NOT NULL,
	user_name VARCHAR(256) NOT NULL,
	user_email VARCHAR(256) NOT NULL,
	user_identity_document_type VARCHAR(64) NULL,
	user_identity_document_code VARCHAR(64) NULL,
	user_identity_document_country_code VARCHAR(3) NULL,
	status VARCHAR(32) NULL,
	created_at TIMESTAMP WITH TIME ZONE NOT NULL,
	created_by VARCHAR(128) NOT NULL,
	updated_at TIMESTAMP WITH TIME ZONE NULL,
	updated_by VARCHAR(128) NULL,
	CONSTRAINT site_users_pk PRIMARY KEY (id) );

CREATE TABLE
	site_cities( id BIGSERIAL NOT NULL,
	name CHARACTER VARYING(255) NOT NULL,
	location CHARACTER VARYING(255) NULL,
	user_id BIGSERIAL NOT NULL,
	CONSTRAINT site_cities_pk PRIMARY KEY (id) );

ALTER TABLE
	c_security_role_permission ADD CONSTRAINT c_security_role_permission_c_security_role FOREIGN KEY (id_role) REFERENCES c_security_role (id);

ALTER TABLE
	c_security_role_permission ADD CONSTRAINT c_security_role_permission_c_security_permission FOREIGN KEY (id_permission) REFERENCES c_security_permission (id);

ALTER TABLE
	c_security_user ADD CONSTRAINT c_security_user_c_security_role FOREIGN KEY (id_role) REFERENCES c_security_role (id);

ALTER TABLE
	c_security_user_password ADD CONSTRAINT c_security_user_password_c_security_user FOREIGN KEY (id_user) REFERENCES c_security_user (id);

ALTER TABLE
	site_cities ADD CONSTRAINT site_cities_site_users FOREIGN KEY (user_id) REFERENCES site_users (id);