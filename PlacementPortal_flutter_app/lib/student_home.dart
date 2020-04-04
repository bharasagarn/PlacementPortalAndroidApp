import 'package:flutter/material.dart';

class StudentHomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Student Home"),
      ),
      body: Container(color: Colors.amberAccent,)
    );
  }
}

//class StudentHomeWidget extends StatelessWidget {
//  StudentHomeWidget({Key key}) : super(key: key);
//
//  @override
//  Widget build(BuildContext context) {
//    return Center(
//      child: Column(
//        mainAxisSize: MainAxisSize.min,
//        children: <Widget>[
//          const Text('Student Home page here'),
//        ],
//      ),
//    );
//  }
//}