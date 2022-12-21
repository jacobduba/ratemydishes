import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

Color cardinal = const Color(0xC8102E00);

class MyApp extends StatelessWidget {

  const MyApp({super.key});


  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        useMaterial3: true,
        colorSchemeSeed: cardinal,
        brightness: Brightness.light
      ),
      darkTheme: ThemeData(
        useMaterial3: true,
        colorSchemeSeed: cardinal,
        brightness: Brightness.dark
      ),
      home: const MyHomePage(title: 'Rate My Dishes'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: const [
            Text(
              'Hello world!',
            ),
          ],
        ),
      ),
    );
  }
}
