INSERT INTO users VALUES ('admin', '{noop}admin','12345678','test@domain.com','Hello, I am a demo admin account!');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_ADMIN');