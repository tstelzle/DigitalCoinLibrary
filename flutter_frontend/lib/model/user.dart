class User {
  User({required this.email, required this.idToken});

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      email: json['email'] as String,
      idToken: json['idtoken'] as String,
    );
  }

  String email;
  String idToken;

  Map<String, dynamic> toJson() {
    return {
      'email': email,
      'idtoken': idToken,
    };
  }
}
