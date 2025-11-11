import "@typespec/openapi3";

@doc("Роль пользователя в системе")
enum UserRole {
  ROLE_USER,
  ROLE_ADMIN
}

@doc("DTO для создания пользователя")
model CreateUserDto {
  username: string;
  email: string;
  password: string;
  profile?: CreateProfileDto;
}

@doc("DTO для обновления пользователя")
model UpdateUserDto {
  username?: string;
  email?: string;
  profile?: UpdateProfileDto;
}

@doc("DTO для возврата пользователю на фронт")
model UserResponse {
  id: string;
  username: string;
  email?: string;
  createdAt: utcDateTime;
  roles?: UserRole[];
  profile?: ProfileResponse;
}

@doc("DTO для создания профиля")
model CreateProfileDto {
  avatar?: string;
  bio?: string;
  settings?: {};
}

@doc("DTO для обновления профиля")
model UpdateProfileDto {
  avatar?: string;
  bio?: string;
  settings?: {};
}

@doc("DTO для возврата профиля на фронт")
model ProfileResponse {
  avatar?: string;
  bio?: string;
  settings?: {};
}

@doc("DTO для создания связи пользователя с билдом")
model CreateUserBuildDto {
  buildId: string;
}

@doc("DTO для возврата билда пользователя на фронт")
model UserBuildResponse {
  id: string;
  buildId: string;
  createdAt: utcDateTime;
}