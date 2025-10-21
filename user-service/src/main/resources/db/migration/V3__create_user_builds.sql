CREATE TABLE user_builds (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    build_id UUID,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_userbuilds_user FOREIGN KEY(user_id) REFERENCES users(id)
);