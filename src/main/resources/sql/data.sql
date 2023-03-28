INSERT INTO users VALUES ('keith', '{noop}keithpw', 'admin and user');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_ADMIN');

INSERT INTO users VALUES ('john', '{noop}johnpw', 'admin');
INSERT INTO user_roles(username, role) VALUES ('john', 'ROLE_ADMIN');

INSERT INTO users VALUES ('mary', '{noop}marypw', 'user');
INSERT INTO user_roles(username, role) VALUES ('mary', 'ROLE_USER');