INSERT INTO users VALUES ('carljohnson', '{noop}carljspw','12345678','test@domain.com','Hello, I am a demo user/admin account!');
INSERT INTO user_roles(username, role) VALUES ('carljohnson', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('carljohnson', 'ROLE_ADMIN');

INSERT INTO users VALUES ('bigsmoke', '{noop}melvinpw','23456789','test2@domain.com','Hello, I am a demo user account!');
INSERT INTO user_roles(username, role) VALUES ('bigsmoke', 'ROLE_USER');

INSERT INTO users VALUES ('sweetjohnson', '{noop}seanjspw','34567890','test3@domain.com','Hello, I am a demo admin account!');
INSERT INTO user_roles(username, role) VALUES ('sweetjohnson', 'ROLE_ADMIN');