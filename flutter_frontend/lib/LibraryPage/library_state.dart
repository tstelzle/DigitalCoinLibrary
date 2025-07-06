import 'package:flutter_frontend/model/edition.dart';

class LibraryState {
  LibraryState({
    required this.editions,
    required this.title,
  });
  final List<Edition> editions;
  final String title;
}
