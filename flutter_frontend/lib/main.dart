import 'package:flutter/material.dart';
import 'package:flutter_frontend/core/coin_api.dart';
import 'package:flutter_frontend/injection_container.dart' as injectionContainer;
import 'package:provider/provider.dart';

import 'home/home.dart';

void main() {
  injectionContainer.init();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => CoinList(coinApi: injectionContainer.serviceLocator<CoinApi>()),
        child: MaterialApp(
          title: 'Coin Library',
          theme: ThemeData(
            primarySwatch: Colors.blue,
          ),
          home: const MyHomePage(title: 'Coin Library'),
        ));
  }
}
