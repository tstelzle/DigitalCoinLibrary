import 'package:flutter/material.dart';
import 'package:flutter_frontend/core/constants.dart';
import 'package:flutter_frontend/model/edition.dart';

class Coin {
  Coin({
    required this.id,
    required this.edition,
    required this.year,
    required this.special,
    required this.name,
    required this.coinSize,
    required this.available,
    required this.imagePath,
  });

  factory Coin.fromJson(Map<String, dynamic> json) {
    return Coin(
      id: json['id'] as int,
      edition: Edition.fromJson(json['edition'] as Map<String, dynamic>),
      year: (json['year'] ?? -1) as int,
      special: (json['special'] ?? false) as bool,
      name: (json['name'] ?? '') as String,
      coinSize: (json['size'] ?? -1) as int,
      available: (json['available'] ?? false) as bool,
      imagePath: (json['imagePath'] ?? '') as String,
    );
  }

  final int id;
  final Edition edition;
  final int year;
  final bool special;
  final String name;
  final int coinSize;
  late bool available;
  final String imagePath;
  late MaterialColor availableColor;
  String frontImagePath() => '$frontImage$coinSize';

  @override
  String toString() {
    return 'Coin: ${edition.country}, $year, $special, $name, $coinSize';
  }

  // TODO can somehow be solved smarter
  void setAvailableColor(bool isAvailable, String librarianId) {
    if (librarianId == '') {
      if (isAvailable) {
        availableColor = Colors.green;
      }
      availableColor = Colors.red;
    }
    availableColor = Colors.blue;
  }
}
