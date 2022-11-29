import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'home/home.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => CoinList(),
        child: MaterialApp(
          title: 'Coin Library',
          theme: ThemeData(
            primarySwatch: Colors.blue,
          ),
          home: const MyHomePage(title: 'Coin Library'),
        ));
  }
}
