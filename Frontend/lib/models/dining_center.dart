class DiningCenter {
  final int id;
  final String name;
  final String location;
  final String description;

  const DiningCenter(
      {required this.id,
      required this.name,
      required this.location,
      required this.description});

  factory DiningCenter.fromJson(Map<String, dynamic> json) {
    return DiningCenter(
        id: json['id'],
        name: json['name'],
        location: json['location'],
        description: json['description']);
  }
}
