import 'package:flutter/material.dart';
import 'package:flutter_frontend/core/constants.dart';

class Coin {
  Coin({
    required this.id,
    required this.year,
    required this.special,
    required this.name,
    required this.coinSize,
    required this.available,
    required this.imagePath,
    required this.availableColor,
    required this.frontImagePath,
  });

  factory Coin.fromJson(Map<String, dynamic> json, String librarianId) {
    final available = (json['available'] ?? false) as bool;
    final availableColor = _determineAvailableColor(available, librarianId);
    final coinSize = (json['size'] ?? -1) as int;
    final imagePath = (json['imagePath'] ?? '') as String;

    return Coin(
      id: json['id'] as int,
      year: (json['year'] ?? -1) as int,
      special: (json['special'] ?? false) as bool,
      name: (json['name'] ?? '') as String,
      coinSize: coinSize,
      available: available,
      imagePath: generateUri('/api/images/download', {'imageUrl': imagePath}).toString(),
      availableColor: availableColor,
      frontImagePath: generateUri('$frontImage$coinSize', {}).toString(),
    );
  }

  final int id;
  final int year;
  final bool special;
  final String name;
  final int coinSize;
  final bool available;
  final String imagePath;
  final int availableColor;
  final String frontImagePath;

  @override
  String toString() {
    return 'Coin: $year, $special, $name, $coinSize';
  }

  static int _determineAvailableColor(
    bool isAvailable,
    String librarianId,
  ) {
    if (librarianId != '') {
      if (isAvailable) {
        return Colors.green.toARGB32();
      }
      return Colors.red.toARGB32();
    }
    return Colors.blue.toARGB32();
  }
}
