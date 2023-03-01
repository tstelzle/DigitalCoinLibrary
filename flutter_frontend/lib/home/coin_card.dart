import 'package:flutter/material.dart';

import '../model/coin.dart';

class FlipCard extends StatefulWidget {
  final Coin coin;

  const FlipCard({super.key, required this.coin});

  @override
  State<FlipCard> createState() => _FlipCardState();
}

class _FlipCardState extends State<FlipCard> {
  bool showText = true;

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () {
        setState(() {
          showText = !showText;
        });
      },
      child: Card(
          color: widget.coin.available ? Colors.green : Colors.red,
          child: showText
              ? ListTile(
                  title: Text(widget.coin.coinSize >= 100
                      ? "${widget.coin.coinSize / 100}€"
                      : "${widget.coin.coinSize} ct."),
                  subtitle: widget.coin.special ? Text(widget.coin.name) : null)
              : widget.coin.imagePath.isEmpty
                  ? const ListTile(
                      title: Text("No Image Available."),
                    )
                  : Image.network(
                      widget.coin.imagePath,
                      fit: BoxFit.cover,
                    )),
    );
  }
}
