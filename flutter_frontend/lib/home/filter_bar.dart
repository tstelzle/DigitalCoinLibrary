import 'package:flutter/material.dart';

class FilterBar extends StatefulWidget {
  const FilterBar({super.key});

  @override
  State<FilterBar> createState() => _FilterBarState();
}

class _FilterBarState extends State<FilterBar> {
  bool isSpecial = false;
  int? coinValue;
  String? country;

  @override
  Widget build(BuildContext context) {
    return Wrap(
      children: <Widget>[
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text("Sondermünze:"),
            Padding(
                padding: const EdgeInsets.fromLTRB(0.0, 20.0, 20.0, 20.0),
                child: Switch(
                  value: isSpecial,
                  activeColor: Colors.blue,
                  onChanged: (bool value) {
                    setState(() {
                      isSpecial = value;
                    });
                  },
                )),
            const Text("Münzgröße:"),
            Padding(
                padding: const EdgeInsets.fromLTRB(10.0, 20.0, 20.0, 20.0),
                child: DropdownButton<int>(
                  value: coinValue,
                  onChanged: (int? newValue) {
                    setState(() {
                      coinValue = newValue!;
                    });
                  },
                  items: <int?>[null, 0, 1, 2, 5, 10, 20, 50, 100, 200]
                      .map<DropdownMenuItem<int>>((int? value) {
                    return DropdownMenuItem<int>(
                      value: value,
                      // TODO Adjust visible value here
                      child: Text(value != null ? value.toString() : '-'),
                    );
                  }).toList(),
                )),
            const Text("Land:"),
            Padding(
                padding: const EdgeInsets.fromLTRB(10.0, 20.0, 20.0, 20.0),
                child: DropdownButton<String>(
                  value: country,
                  onChanged: (String? newValue) {
                    setState(() {
                      country = newValue!;
                    });
                  },
                  items: <String?>[null, 'USA', 'Canada', 'Mexico']
                      .map<DropdownMenuItem<String>>((String? value) {
                    return DropdownMenuItem<String>(
                      value: value,
                      child: Text(value != null ? value.toString() : '-'),
                    );
                  }).toList(),
                ))
          ],
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
            Padding(
                padding: const EdgeInsets.all(20.0),
                child: ElevatedButton(
                  onPressed: () {
                    // Clear button logic
                  },
                  child: const Text('Clear'),
                )),
            Padding(
                padding: const EdgeInsets.all(20.0),
                child: ElevatedButton(
                  onPressed: () {
                    // Apply button logic
                  },
                  child: const Text('Apply'),
                )),
          ],
        ),
      ],
    );
  }
}
