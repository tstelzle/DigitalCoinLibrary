import 'package:flutter_frontend/core/coin_api.dart';
import 'package:get_it/get_it.dart';

final GetIt serviceLocator = GetIt.instance;

void init() {
  serviceLocator.registerLazySingleton(CoinApi.new);
}
