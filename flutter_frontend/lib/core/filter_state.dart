import 'package:flutter_bloc/flutter_bloc.dart';

// State
class FilterState {

  FilterState(this.coinSize, this.country);
  final int coinSize;
  final String country;
}

// Bloc
class FilterCubit extends Cubit<FilterState> {
  FilterCubit() : super(FilterState(0, 'all'));

  void updateFilterState(int coinSize, String country) {
    emit(FilterState(coinSize, country));
  }
}
