import 'package:flutter/material.dart';
import 'package:flutter_frontend/model/edition.dart';
import 'package:flutter_frontend/LibraryPage/coin_view.dart';

class EditionView extends StatelessWidget {
  const EditionView({required this.edition, super.key});

  final Edition edition;

  @override
  Widget build(BuildContext context) {
    return Column(
      children: <Widget>[
        ListTile(title: Text(edition.editionString)),
        GridView.builder(
          physics: const ScrollPhysics(),
          shrinkWrap: true,
          gridDelegate: const SliverGridDelegateWithMaxCrossAxisExtent(
            maxCrossAxisExtent: 100,
            mainAxisExtent: 100,
            crossAxisSpacing: 5,
            mainAxisSpacing: 5,
          ),
          itemCount: edition.coins.length,
          itemBuilder: (BuildContext context, int index) {
            return CoinView(coin: edition.coins[index]);
          },
        ),
      ],
    );
  }
}
