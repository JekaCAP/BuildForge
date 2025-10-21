CREATE TABLE user_roles (
    user_id UUID NOT NULL,
    role VARCHAR(50) NOT NULL,
    CONSTRAINT fk_userroles_user FOREIGN KEY(user_id) REFERENCES users(id)
);