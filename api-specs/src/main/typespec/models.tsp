import "@typespec/openapi3";
import "./dto.tsp";

@doc("Профиль пользователя")
model Profile {
  id?: string;
  userId: string;
  avatar?: string;
  bio?: string;
  settings?: {};
}

@doc("Пользователь системы")
model User {
  id?: string;
  username: string;
  email?: string;
  hashedPassword?: string;
  createdAt?: utcDateTime;
  updatedAt?: utcDateTime;
  profile?: Profile;
  roles?: RoleName[];
}

@doc("Сборки пользователя")
model UserBuild {
  id?: string;
  userId: string;
  buildId: string;
  createdAt?: utcDateTime;
}