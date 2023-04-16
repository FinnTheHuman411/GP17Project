DROP TABLE IF EXISTS attachment;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS image;

create table if not exists image (
    id bigint generated by default as identity,
    description varchar(255),
    username varchar(255),
    title varchar(255),
    upload_time timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    update_time timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    primary key (id)
);
create table if not exists attachment (
    id uuid default random_uuid() not null,
    content blob,
    content_type varchar(255),
    filename varchar(255),
    post_id bigint,
    primary key (id),
    foreign key (post_id) references image
);
create table if not exists comment (
    id bigint generated by default as identity,
    text varchar(255),
    upload_time timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    update_time timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    post_id bigint,
    username varchar(255),
    primary key (id),
    foreign key (post_id) references image
);

DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY (username)
);
CREATE TABLE IF NOT EXISTS user_roles (
    user_role_id INTEGER GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (username) REFERENCES users(username)
);