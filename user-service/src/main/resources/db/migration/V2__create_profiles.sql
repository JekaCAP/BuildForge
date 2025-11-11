CREATE TABLE profiles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    avatar TEXT,
    bio TEXT,
    setting JSONB,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING'
);
