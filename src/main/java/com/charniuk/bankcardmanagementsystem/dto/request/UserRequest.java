package com.charniuk.bankcardmanagementsystem.dto.request;

import com.charniuk.bankcardmanagementsystem.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание пользователя")
public class UserRequest {

  @Schema(description = "Адрес электронной почты", example = "ivanov@mail.ru")
  @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
  @NotBlank(message = "Адрес электронной почты не может быть пустыми")
  @Email(message = "Email адрес должен быть в формате user@example.com")
  private String email;

  @Schema(description = "Пароль", example = "my1secret!password")
  @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 100 символов")
  @NotBlank(message = "Пароль не может быть пустым")
  private String password;

  @Schema(description = "Имя пользователя", example = "Иван Иванов")
  @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
  @NotBlank(message = "Имя пользователя не может быть пустым")
  private String name;

  @Schema(description = "Роль пользователя", example = "ROLE_USER")
  @NotBlank(message = "Роль не может быть пустой")
  @Pattern(regexp = "ROLE_USER|ROLE_ADMIN", message = "Допустимые роли: ROLE_USER или ROLE_ADMIN")
  private Role role;

}