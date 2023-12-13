import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/core/edition_api.dart';
import 'package:flutter_frontend/core/filter_state.dart';

class FilterBar extends StatefulWidget {

  const FilterBar({required this.filterState, super.key});
  final FilterState filterState;

  @override
  State<FilterBar> createState() => _FilterBarState();
}

class _FilterBarState extends State<FilterBar> {
  final EditionApi editionApi = EditionApi();

  String displayCoinValue(int value) {
    if (value == -1) {
      return 'Spezial';
    } else if (value == 0) {
      return 'Alle';
    } else if (value == 100 || value == 200) {
      return '${value / 100}€';
    } else {
      return '${value}ct';
    }
  }

  @override
  Widget build(BuildContext context) {
    return Wrap(
      children: <Widget>[
        Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text('Münzgröße:'),
            Padding(
                padding: const EdgeInsets.fromLTRB(10, 20, 20, 20),
                child: DropdownButton<int>(
                  value: widget.filterState.coinSize,
                  onChanged: (int? coinSize) {
                    context.read<FilterCubit>().updateFilterState(
                        coinSize!, widget.filterState.country,);
                  },
                  items: <int?>[-1, 0, 1, 2, 5, 10, 20, 50, 100, 200]
                      .map<DropdownMenuItem<int>>((int? value) {
                    return DropdownMenuItem<int>(
                      value: value,
                      child: Text(displayCoinValue(value!)),
                    );
                  }).toList(),
                ),),
            const Text('Land:'),
            Padding(
                padding: const EdgeInsets.fromLTRB(10, 20, 20, 20),
                child: FutureBuilder<List<String>>(
                  future: editionApi.fetchCountries(),
                  builder: (BuildContext context,
                      AsyncSnapshot<List<String>> snapshot,) {
                    if (snapshot.hasData) {
                      return DropdownButton<String>(
                        value: widget.filterState.country,
                        onChanged: (String? country) {
                          context.read<FilterCubit>().updateFilterState(
                              widget.filterState.coinSize, country!,);
                        },
                        items: snapshot.data
                            ?.map<DropdownMenuItem<String>>((String? value) {
                          return DropdownMenuItem<String>(
                            value: value,
                            child: Text(
                                value != 'all' ? value.toString() : 'Alle',),
                          );
                        }).toList(),
                      );
                    } else {
                      return const CircularProgressIndicator(
                          color: Colors.black,);
                    }
                  },
                ),),
          ],
        ),
      ],
    );
  }
}
