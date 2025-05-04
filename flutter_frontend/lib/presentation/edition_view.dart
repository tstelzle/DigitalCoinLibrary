import 'package:flutter/material.dart';
import 'package:flutter_frontend/home/coin_card.dart';
import 'package:flutter_frontend/model/edition.dart';

class EditionView extends StatelessWidget {
  const EditionView(
      {required this.edition, required this.librarianId, super.key});

  final Edition edition;
  final String? librarianId;

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
        builder: (BuildContext context, BoxConstraints constraints) {
      final screenWidth = constraints.maxWidth;
      const itemWidth =
          100.0; // Adjust this value based on your item's desired width
      final crossAxisCount = (screenWidth / itemWidth).floor();
      return Column(
        children: <Widget>[
          ListTile(title: Text(edition.editionString)),
          GridView.builder(
            physics: const ScrollPhysics(),
            shrinkWrap: true,
            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: crossAxisCount,
              crossAxisSpacing: 5,
              mainAxisSpacing: 5,
            ),
            itemCount: edition.coins.length,
            itemBuilder: (BuildContext context, int index) {
              return CoinCard(
                  coin: edition.coins[index],
                  librarianAvailable: librarianId != null);
            },
          ),
        ],
      );
    });
  }
}
