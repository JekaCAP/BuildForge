import "@typespec/http";
import "@typespec/openapi3";
import "./dto.tsp";
import "./models.tsp";

using TypeSpec.Http;
using TypeSpec.OpenAPI;

@service(#{ title: "User Service" })
namespace UserService {

  @tag("Users")
  interface UserApi {
    @get @route("/users/{userId}")
    getUser(@path userId: string): UserResponse;

    @post @route("/users")
    createUser(@body body: CreateUserDto): UserResponse;

    @put @route("/users/{userId}")
    updateUser(@path userId: string, @body body: UpdateUserDto): UserResponse;

    @delete @route("/users/{userId}")
    deleteUser(@path userId: string): void;

    @get @route("/users/{userId}/builds")
    listUserBuilds(@path userId: string): UserBuildResponse[];
  }

  @tag("Profiles")
  interface ProfileApi {
    @get @route("/profiles/{userId}")
    getProfile(@path userId: string): ProfileResponse;

    @post @route("/profiles")
    createProfile(@body body: CreateProfileDto): ProfileResponse;

    @put @route("/profiles/{userId}")
    updateProfile(@path userId: string, @body body: UpdateProfileDto): ProfileResponse;

    @delete @route("/profiles/{userId}")
    deleteProfile(@path userId: string): void;
  }

  @tag("UserBuilds")
  interface UserBuildApi {
    @get @route("/user-builds/{userBuildId}")
    getUserBuild(@path userBuildId: string): UserBuildResponse;

    @post @route("/user-builds")
    createUserBuild(@body body: CreateUserBuildDto): UserBuildResponse;

    @delete @route("/user-builds/{userBuildId}")
    deleteUserBuild(@path userBuildId: string): void;

    @get @route("/user-builds/user/{userId}")
    listUserBuildsByUser(@path userId: string): UserBuildResponse[];
  }

}