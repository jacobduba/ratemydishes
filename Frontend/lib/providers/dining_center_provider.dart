import 'dart:convert';

import 'package:frontend/models/dining_center.dart';
import 'package:http/http.dart' as http;

const String diningCentersEndpoint =
    'https://jsonplaceholder.typicode.com/albums/1';

Future<DiningCenter> fetchDiningCenters() async {
  final response = await http.get(Uri.parse(diningCentersEndpoint));

  if (response.statusCode == 200) {
    return DiningCenter.fromJson(jsonDecode(response.body));
  } else {
    throw Exception('Failed to load dining centers');
  }
}
