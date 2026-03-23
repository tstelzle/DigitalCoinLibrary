import 'package:flutter/material.dart';

class ImprintPage extends StatelessWidget {
  const ImprintPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          'Impressum',
          style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
        ),
      ),
      body: const SingleChildScrollView(
        padding: EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Kontakinformationen',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            Text('Tarek Stelzle'),
            Text('Karl-Frowein-Str. 21, 53115 Bonn'),
            Text('tarek.stelzle@gmx.de'),
            Text(
              'Quellenangabe für verwendete Bilder & Grafiken',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            Text(
                'Das von uns verwendete Bildmaterial unterliegt dem Copyright der jeweiligen Inhaber und Fotografen. Dies ist die Europäische Zentralbank.'),
          ],
        ),
      ),
    );
  }
}
