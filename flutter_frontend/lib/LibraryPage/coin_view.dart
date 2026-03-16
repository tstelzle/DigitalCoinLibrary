import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:flutter_frontend/LibraryPage/html_image.dart';
import 'package:flutter_frontend/model/coin.dart';

class CoinView extends StatefulWidget {
  const CoinView({required this.coin, super.key});

  final Coin coin;

  @override
  State<CoinView> createState() => _CoinViewState();
}

class _CoinViewState extends State<CoinView> {
  bool showBack = true;

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () => setState(() => showBack = !showBack),
      child: AspectRatio(
        aspectRatio: 1 / 1,
        child: Card(
          shape: const CircleBorder(),
          color: Color(widget.coin.availableColor),
          child: Padding(
            padding: const EdgeInsets.all(8),
            child: ClipOval(
              child: HtmlImageWidget(
                imageUrl: showBack
                    ? widget.coin.imagePath
                    : widget.coin.frontImagePath,
              ),
            ),
          ),
        ),
      ),
    );
  }
}
