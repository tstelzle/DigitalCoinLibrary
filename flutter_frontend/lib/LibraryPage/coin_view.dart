import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:flutter_frontend/LibraryPage/html_image.dart';
import 'package:flutter_frontend/model/coin.dart';

class CoinView extends StatelessWidget {
  const CoinView({required this.coin, super.key});

  final Coin coin;
  @override
  Widget build(BuildContext context) {
    return AspectRatio(
      aspectRatio: 1 / 1,
      child: Card(
        shape: const CircleBorder(),
        color: Color(coin.availableColor),
        child: Padding(
          padding: const EdgeInsets.all(8),
          child: ClipOval(
            // child: CachedNetworkImage(imageUrl: coin.imagePath),
            child: HtmlImageWidget(imageUrl: coin.imagePath),
          ),
        ),
      ),
    );
  }
}
