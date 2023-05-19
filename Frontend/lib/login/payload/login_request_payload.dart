// To parse this JSON data, do
//
//     final loginRequestPayload = loginRequestPayloadFromJson(jsonString);

import 'dart:convert';

LoginRequestPayload loginRequestPayloadFromJson(String str) =>
    LoginRequestPayload.fromJson(json.decode(str));

String loginRequestPayloadToJson(LoginRequestPayload data) =>
    json.encode(data.toJson());

class LoginRequestPayload {
  LoginRequestPayload({
    required this.netId,
    required this.password,
  });

  String netId;
  String password;

  factory LoginRequestPayload.fromJson(Map<String, dynamic> json) =>
      LoginRequestPayload(
        netId: json["netId"],
        password: json["password"],
      );

  Map<String, dynamic> toJson() => {
        "netId": netId,
        "password": password,
      };
}
