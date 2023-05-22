import 'package:flutter/material.dart';
import 'package:flutter_frontend/core/constants.dart';

import '../model/coin.dart';

class CoinCard extends StatefulWidget {
  final Coin coin;

  const CoinCard({super.key, required this.coin});

  @override
  State<CoinCard> createState() => _CoinCardState();
}

class _CoinCardState extends State<CoinCard> {
  bool showBack = true;

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () {
        setState(() {
          showBack = !showBack;
        });
      },
      child: Card(
          color: widget.coin.available ? Colors.green : Colors.red,
          child: showBack
              ? widget.coin.imagePath.isEmpty
                  ? const FittedBox(child: Icon(Icons.do_disturb))
                  : ClipOval(
                      child: Image.network(widget.coin.imagePath,
                          fit: BoxFit.cover, errorBuilder:
                              (BuildContext context, Object exception,
                                  StackTrace? stackTrace) {
                      return networkError();
                    }))
              : Image.network(
                  generateUri("$frontImage${widget.coin.coinSize}", {})
                      .toString(),
                  fit: BoxFit.cover, errorBuilder: (BuildContext context,
                      Object exception, StackTrace? stackTrace) {
                  return networkError();
                })),
    );
  }

  Widget networkError() {
    return const FittedBox(child: Icon(Icons.wifi_off));
  }
}
