import 'package:flutter/material.dart';
import 'dart:async';

void main() {
  runApp(const CounterDemoFlutter());
}

class CounterDemoFlutter extends StatelessWidget {
  const CounterDemoFlutter({super.key});

  @override
  Widget build(BuildContext context) {
    const String title = 'Counter Demo Flutter';
    return const MaterialApp(
      title: title,
      home: CounterDemoHomePage(title: title),
    );
  }
}

class CounterDemoHomePage extends StatefulWidget {
  const CounterDemoHomePage({super.key, required this.title});

  final String title;

  @override
  State<CounterDemoHomePage> createState() => _CounterDemoHomePageState();
}

class _CounterDemoHomePageState extends State<CounterDemoHomePage> {
  int a = 10;
  int b = 20;

  Timer? timerA;
  Timer? timerB;

  Timer _createTimer(int delay, void Function() callback) {
    return Timer.periodic(Duration(milliseconds: delay), (timer) {
      setState(() {
        callback();
      });
    });
  }

  @override
  void initState() {
    super.initState();
    timerA = _createTimer(2000, () {
      if (++a == 20) timerA?.cancel();
    });
    timerB = _createTimer(4000, () {
      if (++b == 25) timerB?.cancel();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              '$a',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            Text(
              '$b',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
          ],
        ),
      ),
    );
  }
}
