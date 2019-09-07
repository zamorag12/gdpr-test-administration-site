INSERT INTO c_security_permission (id, permission_label, permission_value) VALUES
('UNIR_ALL', 'All admin User', 'ROLE_ADMIN_ALL'),
('MASTER_UNIR', 'City users', 'ROLE_MASTER_CITY');

INSERT INTO c_security_role (id, description, name) VALUES
('ADMINISTRATOR', 'Application Administrator', 'Administrator');

INSERT INTO c_security_role_permission (id_role, id_permission) VALUES
('ADMINISTRATOR', 'UNIR_ALL'),
('ADMINISTRATOR', 'MASTER_UNIR');

INSERT INTO c_security_user (id, active, username, id_role) VALUES
('admin', true,'admin', 'ADMINISTRATOR');

INSERT INTO c_security_user_password (id_user, password) VALUES
('admin', '$2a$10$wT/obdcvI07jSor8roN3NeuWhEdlarvunJ14ZOAFdo1g/lZ1uHbGe');