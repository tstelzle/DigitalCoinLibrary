import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';

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
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Kontakinformationen',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const Text('Tarek Stelzle'),
            const Text('Karl-Frowein-Str. 21, 53115 Bonn'),
            const Text('tarek.stelzle@gmx.de'),
            const Text(
              'Quellenangabe für verwendete Bilder & Grafiken',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const Text(
              'Das von uns verwendete Bildmaterial unterliegt dem Copyright der jeweiligen Inhaber und Fotografen. Dies ist die Europäische Zentralbank.',
            ),
            TextButton(
              onPressed: () async => {
                await launchUrl(
                  Uri.parse(
                    'https://www.ecb.europa.eu/euro/coins/html/index.en.html',
                  ),
                )
              },
              child: const Text(
                'https://www.ecb.europa.eu/euro/coins/html/index.en.html',
              ),
            ),
          ],
        ),
      ),
    );
  }
}
