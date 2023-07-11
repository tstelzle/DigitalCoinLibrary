import 'package:flutter_bloc/flutter_bloc.dart';

abstract class FilterEvent {}

class CoinSizeFilterEvent extends FilterEvent {
  final int value;

  CoinSizeFilterEvent(this.value);
}

class CountryFilterEvent extends FilterEvent {
  final String value;

  CountryFilterEvent(this.value);
}

// State
class FilterState {
  // final bool special;
  final int coinSize;
  final String country;

  FilterState(this.coinSize, this.country);
}

// Bloc
class FilterBloc extends Bloc<FilterEvent, FilterState> {
  FilterBloc() : super(FilterState(0, "all")) {
    on<CoinSizeFilterEvent>(
        (event, emit) => emit(FilterState(event.value, state.country)));
    on<CountryFilterEvent>(
        (event, emit) => emit(FilterState(state.coinSize, event.value)));
  }
}
